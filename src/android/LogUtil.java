package com.rohon.log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 日志工具文件
 */
public class LogUtil extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("init")) {
            String fileDir = args.getString(0);
            String fileName = args.getString(1);
            this.init(fileDir, fileName,callbackContext);
            return true;
        }else if(action.equals("debug")){
            String tag = args.getString(0);
            String info = args.getString(1);
            debug(tag,info); 
            return true;
        }else if(action.equals("info")){
            String tag = args.getString(0);
            String info = args.getString(1);
            info(tag,info); 
            return true;
        }else if(action.equals("warn")){
            String tag = args.getString(0);
            String info = args.getString(1);
            warn(tag,info); 
            return true;
        }else if(action.equals("error")){
            String tag = args.getString(0);
            String info = args.getString(1);
            error(tag,info);
            return true;
        }else if(action.equals("quit")){
            boolean quit = args.getBoolean(0);
            quit(quit); 
            return true;
        }
        return false;
    }

    private void init(String fileDir, String fileName,CallbackContext callbackContext) {
        if (fileDir != null && fileDir.length() > 0 && fileName != null && fileName.length() > 0) {
            Log.init(fileDir,fileName);
            callbackContext.success("初始化成功");
        } else {
            callbackContext.error("请传入文件名和文件目录");
        }
    }

    private void debug(String tag,String info){
        Log.debug(tag,info);
    }

    private void info(String tag,String info){
        Log.error(tag,info);
    }

    private void warn(String tag,String info){
        Log.warn(tag,info);
    }

    private void error(String tag,String info){
        Log.error(tag,info);
    }
    private void quit(boolean quit){
        Log.quit(quit);
    }
}
