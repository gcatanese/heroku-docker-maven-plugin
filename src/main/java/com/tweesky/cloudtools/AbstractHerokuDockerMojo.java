package com.tweesky.cloudtools;

import com.tweesky.cloudtools.model.AppInfo;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.maven.project.MavenProject;

/**
 * Heroku Docker operations
 */
public abstract class AbstractHerokuDockerMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject mavenProject;

    /**
     * Heroku application name
     */
    @Parameter(name = "appName", required = false)
    protected String appName = null;

    /**
     * Type of process (web | worker)
     */
    @Parameter(name = "processType")
    protected String processType = null;

    /**
     * ConfigVars for the application
     */
    @Parameter(name = "configVars")
    protected Map<String, String> configVars = Collections.emptyMap();

    protected CustomPluginLogger customPluginLogger;

    public CustomPluginLogger getCustomLog() {
        if (this.customPluginLogger == null) {
            this.customPluginLogger = new CustomPluginLogger(this.mavenProject.getName());
        }
        return this.customPluginLogger;
    }

    /**
     * Execute command
     *
     * @param parameters Command (with parameters) to execute
     * @throws IOException throws IOException when command execution fails
     */
    protected void execCommand(String... parameters) throws IOException {

        getCustomLog().info("Running " + String.join(" ", parameters));
        getCustomLog().debug("Working dir " + mavenProject.getBasedir());

        Process proc = Runtime.getRuntime().exec(parameters, null, mavenProject.getBasedir());
        logProcessOutput(proc);

    }

    /**
     *  Print Heroku application info
     * @throws MojoExecutionException
     */
    protected void printAppInfo() throws MojoExecutionException {
        this.printAppInfo(false);
    }

    /**
     * Print Heroku application info
     * @param extended when true include extra information (i.e. command to run the Dyno)
     * @throws MojoExecutionException
     */
    protected void printAppInfo(boolean extended) throws MojoExecutionException {
        try {

            String[] cmd = new String[]{"heroku", "apps:info", "-j", "-a", this.appName};
            Process proc = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String stdErr = stdError.lines().collect(Collectors.joining());
            if (!stdErr.isEmpty()) {
                getCustomLog().warn(stdErr);
            }

            AppInfo appInfo = new AppInfo(stdInput.lines().collect(Collectors.joining()));

            getCustomLog().info(appInfo.getName() + " (region " + appInfo.getRegion() + ")" +
                    " " + appInfo.getWeb_url());
            if (extended) {
                getCustomLog().info("command: " + appInfo.getCommand() +
                        " repo_size: " + appInfo.getRepo_size());
            }

        } catch (Exception e) {
            getCustomLog().error(e.getMessage(), e);
            throw new MojoExecutionException("");
        }
    }

    /**
     * Scale up the Dyno to 1 instance
     * @throws MojoExecutionException
     */
    protected void start() throws MojoExecutionException {
        scale("1");
    }

    /**
     * Scale down the Dyno to 0 instances
     * @throws MojoExecutionException
     */
    protected void stop() throws MojoExecutionException {
        scale("0");
    }

    // perform the Heroku scaling
    private void scale(String numOfInstances) throws MojoExecutionException {
        try {

            String[] cmd = new String[]{"heroku", "ps:scale", this.processType + "=" + numOfInstances, "-a", this.appName};
            getCustomLog().info("Running " + String.join(" ", cmd));

            Process proc = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String stdErr = stdError.lines().collect(Collectors.joining());
            if (!stdErr.isEmpty()) {
                getCustomLog().warn(stdErr);
            }

            getCustomLog().info("Stopping " + this.appName + "(" + this.processType + ")");

        } catch (Exception e) {
            getCustomLog().error(e.getMessage(), e);
            throw new MojoExecutionException("");
        }
    }

    protected boolean isParentPom() {
        return !this.mavenProject.getModules().isEmpty();
    }

    protected boolean isAppNameNotFound() {
        return this.appName == null;
    }

    protected boolean isDockerfileNotFound() {
        return !new File(mavenProject.getBasedir() + "/Dockerfile").exists();
    }

    private void logProcessOutput(Process proc) throws IOException {

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;

        // standard output
        while ((s = stdInput.readLine()) != null) {
            getCustomLog().debug(s);
        }
        // standard error (if any)
        while ((s = stdError.readLine()) != null) {
            getCustomLog().debug(s);
        }

    }

}
