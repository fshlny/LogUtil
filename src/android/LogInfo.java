package com.rohon.log;

/**
 * 日志信息
 */
public class LogInfo {
    /**
     *  日志级别
      */
    public static enum LogLevel{
        DEBUG,
        INFO,
        WARN,
        ERROR;

        public String getLevel(){
            return name();
        }
    }

    /**
     *  日志等级
     */
    private LogLevel level;
    /**
     * 时间戳
     */
    private String timeStemp;
    /**
     * TAG
     */
    private String TAG;
    /**
     * info
     */
    private String info;

    public LogInfo(LogLevel level, String timeStamp, String TAG, String info) {
        this.level = level;
        this.timeStemp = timeStamp;
        this.TAG = TAG;
        this.info = info;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getTimeStamp() {
        return timeStemp;
    }

    public String getTAG() {
        return TAG;
    }

    public String getInfo() {
        return info;
    }
}
