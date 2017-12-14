package com.rohon.log;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;
/**
* 日志队列
*/
public class LogQueue implements Runnable{
    private String TAG = LogQueue.class.getSimpleName();
    private LinkedBlockingQueue<LogInfo> logQueue;
    private Thread thread;
    private volatile boolean quit = false;
    private LogWriter writer;
    public LogQueue(String dir,String fileName){
        logQueue = new LinkedBlockingQueue<LogInfo>();
        thread = new Thread(this,"LogQueueThread");
        writer = new LogWriter(dir,fileName);
        thread.start();
    }

    public void putLog(LogInfo info){
        if(info == null) return;
        try {
            logQueue.put(info);
        } catch (InterruptedException e) {
            Log.d(TAG,"put LogInfo to logQueue error,Thread interrupted");
        }
    }

    @Override
    public void run() {
        takeLogInfo();
    }

    private void takeLogInfo(){
        for(;;){
            if(quit){//暂时直接退出
                break;
            }
            try {
                LogInfo log = logQueue.take();//如果没有日志内容，线程会进入等待状态
                writer.writeLog(log);
            } catch (InterruptedException e) {
                Log.d(TAG,"logQueue take logInfo error,Thread interrupted,"+e.getMessage());
            }
        }
    }

    public void quit(boolean quit){
        this.quit = quit;
    }
}
