package com.onerpc.core.util;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @version $Id: LoggerHelper.java
 */
public class LoggerHelper {
    /**
     * private constructor
     */
    private LoggerHelper() {

    }

    /**
     * debug log
     *
     * @param logger
     * @param format
     * @param argArray
     */
    public static void debug(Logger logger, String format, Object... argArray) {
        if (logger.isDebugEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
            logger.debug(formattingTuple.getMessage());
        }
    }

    /**
     * debug log with exception
     *
     * @param logger
     * @param format
     * @param e
     * @param argArray
     */
    public static void debug(Logger logger, String format, Throwable e, Object... argArray) {
        if (logger.isDebugEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
            logger.debug(formattingTuple.getMessage(), e);
        }
    }

    /**
     * info log
     *
     * @param logger
     * @param format
     * @param argArray
     */
    public static void info(Logger logger, String format, Object... argArray) {
        if (logger.isInfoEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
            logger.info(formattingTuple.getMessage());
        }
    }

    /**
     * info log with exception
     *
     * @param logger
     * @param format
     * @param e
     * @param argArray
     */
    public static void info(Logger logger, String format, Throwable e, Object... argArray) {
        if (logger.isInfoEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
            logger.info(formattingTuple.getMessage(), e);
        }
    }

    /**
     * warn log
     *
     * @param logger
     * @param format
     * @param argArray
     */
    public static void warn(Logger logger, String format, Object... argArray) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
        logger.warn(formattingTuple.getMessage());
    }

    /**
     * warn log with exception
     *
     * @param logger
     * @param format
     * @param e
     * @param argArray
     */
    public static void warn(Logger logger, String format, Throwable e, Object... argArray) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
        logger.warn(formattingTuple.getMessage(), e);
    }

    /**
     * error log
     *
     * @param logger
     * @param format
     * @param argArray
     */
    public static void error(Logger logger, String format, Object... argArray) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
        logger.error(formattingTuple.getMessage());
    }

    /**
     * error log with exception
     *
     * @param logger
     * @param format
     * @param e
     * @param argArray
     */
    public static void error(Logger logger, String format, Throwable e, Object... argArray) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, argArray);
        logger.error(formattingTuple.getMessage(), e);
    }
}