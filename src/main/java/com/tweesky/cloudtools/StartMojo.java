package com.tweesky.cloudtools;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Start app(s)
 *
 */
@Mojo(name = "start")
public class StartMojo extends AbstractHerokuDockerMojo {

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

            start();

        } catch (Exception e) {
            getCustomLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage());
        }

    }
}
