package team.tb.controller;


import org.apache.log4j.Logger;

import java.io.Serializable;

public class BaseController implements Serializable {

    protected Logger logger = Logger.getLogger(this.getClass());

    private static final long serialVersionUID = 6357869213649815390L;

    public static void logBefore(Logger logger, String message){
        logger.info("");
        logger.info("start");
        logger.info(message);
    }

    public static void logAfter(Logger logger, String message){
        logger.info("");
        logger.info(message != null ? message : "");
        logger.info("end");
    }

}
