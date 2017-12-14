package com.rohon.log;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 日志文件写逻辑
 */
public class LogWriter {
    private static final String TAG = LogWriter.class.getSimpleName();
    private String fileDir;
    private String fileName;
    private String sdCardRootPath;
    private File file;
    private ReentrantLock lock;
    public LogWriter(String dir,String name){
        this.fileName = name;
        this.fileDir = dir;
        sdCardRootPath = Environment.getExternalStorageDirectory().getPath();
        createFile();
        lock = new ReentrantLock();
    }

    private void createFile(){
        File dir = new File(sdCardRootPath+File.separator+fileDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        file = new File(dir,fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG,"创建日志文件失败",e);
            }
        }
    }

    public void writeLog(LogInfo info) {
        StringBuffer sb = new StringBuffer();
        sb.append("["+info.getTimeStamp()+"]  ");
        sb.append("["+info.getLevel().getLevel()+"]  ");
        sb.append("["+info.getTAG()+"]  ");
        sb.append(info.getInfo()+"\n");
        FileWriter writer = null;
        PrintWriter print = null;
        lock.lock();
        try {

            writer = new FileWriter(file,true);
            print = new PrintWriter(writer);
            print.print(sb.toString());
//            writer.append(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG,"写文件时，文件不存在",e);
            createFile();//文件不存在就重新创建
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG,"写文件时，UTF-8编码不支持",e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"写文件失败",e);
        }finally {

            if(writer != null){
                try {
                    writer.flush();
                    print.flush();
                    writer.close();
                    print.close();
                } catch (IOException e) {
                    Log.e(TAG,"关闭文件流失败");
                }
            }
            lock.unlock();
        }
    }
}
