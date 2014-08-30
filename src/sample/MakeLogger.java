package sample;

import org.apache.log4j.*;

import java.util.Date;

/**
 * Created by AURANGO SABBIR on 8/31/2014.
 */
public class MakeLogger {
    public static Logger log= Logger.getLogger(MakeLogger.class);
    public static void printToLogger(String class_name,String error_name)
    {
        log.error(new Date().toString()+" ==> "+class_name+" ==> EXCEPTION OCCUR : "+error_name);
    }

    public static void printToLogger(String error_name)
    {
        log.error(new Date().toString()+" ==> EXCEPTION OCCUR : "+error_name);
    }
}
