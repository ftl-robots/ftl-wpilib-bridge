package com.ftlrobots.bridge;

import java.io.File;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public final class LogConfigurator {
    private LogConfigurator() {}

    public static void loadLog4jConfig() {
        URI configurationUri;
        try {
            configurationUri = LogConfigurator.class.getResource("/log4j2.xml").toURI();
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            if (context != null) {
                context.setConfigLocation(configurationUri);
            }
        }
        catch (Exception ex) {
            System.out.println("Error loading config: " + ex.getMessage());
        }
    }
}