package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by min on 2016/11/21.
 */
public class LogbackTest {
    private static Logger log = LoggerFactory.getLogger(LogbackTest.class);
    public static void main(String[] args) {
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }
}
