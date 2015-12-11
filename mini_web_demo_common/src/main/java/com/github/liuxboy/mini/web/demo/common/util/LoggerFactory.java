package com.github.liuxboy.mini.web.demo.common.util;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/8 17:02
 * @comment LoggerFactory
 */
public class LoggerFactory {
    public static Logger getLogger() {
        return new Logger(LoggerFactory.class);
    }

    public static Logger getLogger(String className) {
        return new Logger(className);
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(clazz);
    }
}
