package com.perosa;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Heroku Docker operations
 */
public abstract class AbstractHerokuDockerMojo extends AbstractMojo {

    private Log log;

    @Parameter(name = "appName")
    protected String appName = null;

    @Parameter(name = "processType")
    protected String processType = null;

    public void setLog(Log log) {
        this.log = log;
    }

    public Log getLog() {
        if (this.log == null) {
            this.log = new SystemStreamLog();
        }
        return this.log;
    }


    protected void execCommand(String... parameters) {

        log.info("Running " + String.join(" ", parameters));
        try {

            Process proc = Runtime.getRuntime().exec(parameters);
            logProcessOutput(proc);

        } catch (IOException e) {
            log.error(e);
        }

    }

    private void logProcessOutput(Process proc) throws IOException {

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;

        // standard output
        while ((s = stdInput.readLine()) != null) {
            log.debug(s);
        }
        // standard error (if any)
        while ((s = stdError.readLine()) != null) {
            log.debug(s);
        }

    }

}
