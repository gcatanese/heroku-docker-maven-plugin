package com.perosa;

import com.perosa.model.AppInfo;
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
     * Type of process
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
     * @param parameters
     * @throws IOException
     */
    protected void execCommand(String... parameters) throws IOException {

        getCustomLog().info("Running " + String.join(" ", parameters));
        getCustomLog().info("Working dir " + mavenProject.getBasedir());

        Process proc = Runtime.getRuntime().exec(parameters, null, mavenProject.getBasedir());
        logProcessOutput(proc);

    }

    protected void printAppInfo() throws MojoExecutionException {
        try {

            String[] cmd = new String[]{"heroku", "apps:info", "-j", "-a", this.appName};
            Process proc = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            getCustomLog().warn(stdError.lines().collect(Collectors.joining()));

            AppInfo appInfo = new AppInfo(stdInput.lines().collect(Collectors.joining()));

            getCustomLog().info("Deployed " + appInfo.getName() + " (" + appInfo.getRegion() + ")" +
                    " url: " + appInfo.getWeb_url());

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
