package de.fischboy.fischmod.api.utils.console;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class ConsoleUtil {

    private static final Logger logger = LogManager.getLogger();

    public static void info(String line) {
        logger.info(line);
    }

    public static void warn(String line) {
        logger.warn(line);
    }

    public static void error(String line) {
        logger.error(line);
    }


}