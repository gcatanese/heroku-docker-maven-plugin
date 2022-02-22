package com.perosa;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

/**
 * A custom logger to enrich execution logging
 */
public class CustomPluginLogger {

    protected static Log log;

    private static String mavenProjectName;

    public CustomPluginLogger(String mavenProjectName) {
        this.mavenProjectName = mavenProjectName;

        if (this.log == null) {
            this.log = new SystemStreamLog();
        }
    }

    public void info(String message) {
        log.info(format(message));
    }

    public void debug(String message) {
        log.debug(format(message));
    }

    public void warn(String message) {
        log.warn(format(message));
    }

    public void error(String message) {
        log.error(format(message));
    }

    public void error(String message, Throwable throwable) {
        log.error(format(message), throwable);
    }

    private String format(String message) {
        return ("[" + mavenProjectName + "] " + message);
    }
}
