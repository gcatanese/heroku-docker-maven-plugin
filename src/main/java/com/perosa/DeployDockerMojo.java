package com.perosa;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.logging.Logger;

/**
 * Deploy to Heroku Docker Registry
 *
 * @phase process-sources
 */
@Mojo(name="deploy-docker")
public class DeployDockerMojo extends AbstractHerokuDockerMojo {

    private Logger log = Logger.getLogger(this.getClass().getName());

    public void execute() throws MojoExecutionException {

        if(this.appName == null) {
            throw new MojoExecutionException("Undefined appName");
        }

        if(this.processType == null) {
            throw new MojoExecutionException("Undefined processType");
        }

        // push
        execCommand("heroku", "container:push", this.processType, "-a", this.appName);
        // release
        execCommand("heroku", "container:release", this.processType, "-a", this.appName);
        // set config vars
        this.configVars.entrySet().stream()
                .forEach(e -> execCommand("heroku", "config:set",
                        e.getKey() + "=" + e.getValue(), "-a", this.appName));

    }
}
