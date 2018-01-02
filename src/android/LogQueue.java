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
            try {
                LogInfo log = logQueue.take();//如果没有日志内容，线程会进入等待状态
                writer.writeLog(log);
            } catch (InterruptedException e) {
                flush();
                Log.d(TAG,"quit Log queue");
                break;
            }
        }
    }

    /**
    * 当线程中断时调用，如果队列中还有日志时，就清空队列
    */
    private void flush(){
        if(logQueue.isEmpty()) return;
        Iterator<LogInfo> iterator = logQueue.iterator();
        while(iterator.hasNext()){
            LogInfo info = iterator.next();
            writer.writeLog(info);
        }
    }

    /**
    * 通过中断来退出循环
    */
    public void quit(){
       this.thread.interrupt();
    }
}