package com.rohon.log;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static SimpleDateFormat sdf;
    private static LogQueue queue;

    /**
     *  初始化
     * @param dir
     * @param fileName
     */
    public static void init(String dir,String fileName){
        queue = new LogQueue(dir,fileName);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * debug
     * @param tag
     * @param info
     */
    public static void debug(String tag,String info){
        String timeStamp = sdf.format(new Date());
        LogInfo debug = new LogInfo(LogInfo.LogLevel.DEBUG,timeStamp,tag,info);
        queue.putLog(debug);
    }

    /**
     * info
     * @param tag
     * @param info
     */
    public static void info(String tag,String info){
        String timeStamp = sdf.format(new Date());
        LogInfo infoL = new LogInfo(LogInfo.LogLevel.INFO,timeStamp,tag,info);
        queue.putLog(infoL);
    }

    /**
     * warn
     * @param tag
     * @param info
     */
    public static void warn(String tag,String info){
        String timeStamp = sdf.format(new Date());
        LogInfo warn = new LogInfo(LogInfo.LogLevel.INFO,timeStamp,tag,info);
        queue.putLog(warn);
    }

    /**
     * error
     * @param tag
     * @param info
     */
    public static void error(String tag,String info){
        String timeStamp = sdf.format(new Date());
        LogInfo warn = new LogInfo(LogInfo.LogLevel.INFO,timeStamp,tag,info);
        queue.putLog(warn);
    }
    
    /**
    * 退出
    */
    public static void quit(boolean quit){
        queue.quit(quit);
    }
}
