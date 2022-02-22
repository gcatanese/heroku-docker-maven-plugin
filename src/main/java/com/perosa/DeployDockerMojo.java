package com.perosa;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Map;

/**
 * Deploy to Heroku Docker Registry
 *
 * @phase process-sources
 */
@Mojo(name = "deploy-docker")
public class DeployDockerMojo extends AbstractHerokuDockerMojo {

    public void execute() throws MojoExecutionException {

        try {

            if (isParentPom()) {
                return;
            }

            if (isAppNameNotFound()) {
                return;
            }

            if (isDockerfileNotFound()) {
                getCustomLog().info("["+this.mavenProject.getName()+"] skip (Dockerfile not found)");
                return;
            }

            // push
            execCommand("heroku", "container:push", this.processType, "-a", this.appName);
            // release
            execCommand("heroku", "container:release", this.processType, "-a", this.appName);
            // set config vars
            for (Map.Entry<String, String> e : this.configVars.entrySet()) {
                execCommand("heroku", "config:set",
                        e.getKey() + "=" + e.getValue(), "-a", this.appName);
            }

            printAppInfo();

        } catch (Exception e) {
            getCustomLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage());
        }

    }
}
