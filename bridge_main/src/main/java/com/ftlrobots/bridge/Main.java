package com.ftlrobots.bridge;

import com.ftlrobots.link.FTLLink;
import com.ftlrobots.link.debug.DebugLink;
import com.ftlrobots.link.ros.RosLink;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger sLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Options options = new Options();
        Option linkType = new Option("l", "linkType", true, "Type of Link");
        options.addOption(linkType);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException e) {
            sLogger.log(Level.ERROR, e);
            formatter.printHelp("BridgeMain", options);

        }

        if (cmd != null) {
            String reqLinkType = cmd.getOptionValue("linkType");
        }

        DefaultDataAccessorFactory.initialize();

        FTLLink ftlLink = new DebugLink();

        try {
            sLogger.log(Level.INFO, "Starting BridgeLink");
            LinkRunner linkRunner = new LinkRunner(ftlLink);
            linkRunner.start();

            // TEST
            RosLink rosLink = new RosLink();
            rosLink.start();
        }
        catch (Exception e) {
            sLogger.log(Level.FATAL, e);
            System.exit(-1);
        }
    }
}