var exec = require('cordova/exec');

exports.init = function(dir,fileName, success, error) {
    exec(success, error, "LogUtil", "init", [dir,fileName]);
};

exports.debug = function(tag,info,success,error){
	exec(success,error,"LogUtil","debug",[tag,info]);
};

exports.info = function(tag,info,success,error){
	exec(success,error,"LogUtil","info",[tag,info]);
};

exports.warn = function(tag,info,success,error){
	exec(success,error,"LogUtil","warn",[tag,info]);
};

exports.error = function(tag,info,success,error){
	exec(success,error,"LogUtil","error",[tag,info]);
};

exports.quit = function(quit,success,error){
	exec(success,error,"LogUtil","quit",[quit]);
};