## Cordova插件开发

讲在开头，讨论cordova插件开发难不难其实是一件很无聊的事情，具体功能还要考虑到插件要提供什么样的功能。这篇文章只是一篇对cordova插件开发流程的简介。具体功能还需要具体分析。

## plugman安装

开发cordova插件必须要通过plugman工具来创建一个cordova插件项目。首先就需要各位同学安装好plugman。

```
    npm install -g plugman

```

一开始我们都不知道如何使用plugman，可以通过`plugman help`来查看如何使用

```
E:\CodeMrg\APP_PRO\iapptrade>plugman help
plugman manages plugin.xml-compatible cordova plugins into cordova-generated pr
jects.

Usage
=====

To display this help file, use one of the following:

$ plugman --help
$ plugman -h

To display the plugman version, use one of the following:

 $ plugman --version
 $ plugman -v

Optional flags
--------------

 --debug|-d    : Verbose mode

Install a plugin
----------------

    $ plugman install --platform <platform> --project <directory> --plugin <plu
in> [--variable NAME=VALUE]

Parameters:

 - platform <platform>: One of android, ios, blackberry10, wp8, or windows8
 - project <directory>: Path reference to a cordova-generated project of the pl
tform you specify
 - plugin <plugin>: One of a path reference to a local copy of a plugin, or a r
mote https: or git: URL pointing to a cordova plugin (optionally append #branch
subdir) or a plugin ID from http://plugins.cordova.io
 - variable NAME=VALUE: Some plugins require install-time variables to be defin
d. These could be things like API keys/tokens or other app-specific variables.

 Optional parameters:

 - www <directory>: www assets for the plugin will be installed into this direc
ory. Default is to install into the standard www directory for the platform spe
ified
 - plugins_dir <directory>: a copy of the plugin will be stored in this directo
y. Default is to install into the <project directory>/plugins folder
 - searchpath <directory>: when looking up plugins by ID, look in this director
 and each of its subdirectories for the plugin before hitting the registry.
   Multiple search paths can be used by either specifying the flag multiple tim
s, or by separating paths with a delimiter (: on 'nix, ; on Windows).

Uninstall a plugin
------------------

    $ plugman uninstall --platform <platform> --project <directory> --plugin <p
ugin-id>

Parameters:

 - platform <platform>: One of android, ios, blackberry10, wp8, or windows8
 - project <directory>: Path reference to a cordova-generated project of the pl
tform you specify
 - plugin <plugin-id>: The plugin to remove, identified by its id (see the plug
n.xml's <plugin id> attribute)


Interacting with the registry
=============================

NOTICE: The Cordova Plugin registry became read-only, so the following commands
have been deprecated and removed:

    $ plugman adduser
    $ plugman publish
    $ plugman unpublish
    $ plugman owner add/rm

For managing plugins for the npm registry, use corresponding npm commands. For
ore info on npm commands see `npm help <command>`.
Learn more about publishing your plugins to npm at http://plugins.cordova.io/np
/developers.html

Search for a plugin
-------------------

    $ plugman search <keyword1 keyword2 ...>

Display plugin information
--------------------------

    $ plugman info <pluginID>

Manage registry configuration
-----------------------------
Display current configuration settings:

    $ plugman config ls

Display the current registry URL:

    $ plugman config get registry

Set registry URL:

    $ plugman config set registry <url>

Example:

    $ plugman config set registry http://localhost:5984/registry/_design/app/_r
write

Manage Owners
-------------
Plugin owners are allowed to publish updates to a plugin. To display a list of
wners for a plugin, use:

    $ plugman owner ls <pluginID>

Example:

    $ plugman owner ls org.apache.cordova.core.file

Create A Plugin
---------------

    $ plugman create --name <pluginName> --plugin_id <pluginID> --plugin_versio
 <version> [--path <directory>] [--variable NAME=VALUE]

Parameters:

 - <pluginName>: The name of the plugin
 - <pluginID>: An ID for the plugin, ex: org.bar.foo
 - <version>: A version for the plugin, ex: 0.0.1
 - <directory>: An absolute or relative path for the directory where the plugin
project will be created
 - variable NAME=VALUE: Extra variables such as description or Author

Add a Package.JSON file to plugin
---------------------------------
Creates a package.json file in the plugin based on values from plugin.xml.

 $ plugman createpackagejson <directory>


Add a Platform to a Plugin
--------------------------

    $ plugman platform add --platform_name <platform>

Parameters:

- <platform>: One of android, ios

Remove a Platform from a Plugin
-------------------------------

    $ plugman platform remove --platform_name <platform>

Parameters:

- <platform>: One of android, ios
```

从上面的内容得知使用`plugman create`命令来创建一个插件，那就动手试试

```
plugman create --name LogUtil --plugin_id cordova-plugin-LogUtil --plugin_version 0.0.1

```

* 参数解释

 --name 插件的名称，这里我们要写一个文件日志工具
 
 --plugin_id 插件id，为了和cordova官方保持一致，我们采用了`cordova-plugin-xxx`的形式
 
 --plugin_version 插件版本
 
 好了，现在我们插件已经项目已经创建完成了，让我们看一看他的目录结构吧
 
 
 ![](https://github.com/fshlny/LogUtil/blob/master/images/project_filedir.png)
 

```
├── src
├── www
|  ├── LogUtil.js
└── plugin.xml

```

src是我们源码的路径，但是现在里面什么都没有，需要我们通过`plugman platform add --platform_name xxx`来添加`Android`和`IOS`或者其他平台.由于笔者只会`Android`开发，=所以这里只添加`Android`平台

```
plugman platform add --platform_name android
```

在命令行中运行了上面命令之后，在`src`目录下会生成`android`目录以及`LogUtil.java`文件




## plugin.xml


```

<?xml version='1.0' encoding='utf-8'?>
<plugin id="cordova-plugin-LogUtil" version="0.0.1" xmlns="http://apache.org/cordova/ns/plugins/1.0" xmlns:android="http://schemas.android.com/apk/res/android">
    <name>LogUtil</name>
    <js-module name="LogUtil" src="www/LogUtil.js">
        <clobbers target="cordova.plugins.LogUtil"/>
    </js-module>
    <platform name="android">
        <config-file parent="/*" target="res/xml/config.xml">
            <feature name="LogUtil">
                <param name="android-package" value="cordova-plugin-LogUtil.LogUtil"/>
            </feature>
        </config-file>
        <config-file parent="/*" target="AndroidManifest.xml"/>
        <source-file src="src/android/LogUtil.java" target-dir="src/cordova-plugin-LogUtil/LogUtil"/>
    </platform>
</plugin>

```

* js-module 指定了对应的js文件
* feature 指定了LogUtil的包名,可以修改为com.xxx.xxx格式
* source-file指定源码文件的路径

## LogUtil.js LogUtil.java

LogUtil.js

```
var exec = require('cordova/exec');

exports.coolMethod = function(arg0, success, error) {
    exec(success, error, "LogUtil", "coolMethod", [arg0]);
};
```

LogUtil.java

```
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }
```

结合`LogUtil.js`和`LogUtil.java`可以看出`coolMethod`就是后面js调用的方法,通过方法名来执行不同的业务，有多个方法就拷贝`LogUtil.js`中的`exports.xxx`来声明`js`方法调用方法，将需要的参数传入到`exec`的最后一位参数中，就完成了js方法的声明，接着就是在LogUtil.java中去判断方法名，实现具体的方法

在笔者写这篇文章之前，笔者已经写好了LogUtil插件。这里就不再去将具体的java实现代码，有兴趣的同学可以直接去看源码.

## 在ionic项目中使用cordova插件

1. 声明cordova变量

```
declare var cordova: any;

```

2. 调用cordova插件

```
if(typeof cordova != 'undefined') {
        cordova.plugins.LogUtil.init('mmmmm', 'log.log', success => {
          console.log(`初始化成功:${JSON.stringify(success)}`);
        }, error => {
          console.log(`初始化失败:${JSON.stringify(error)}`);
        });
      }else{
        console.log(`平台初始化完成之后，cordova插件为undefined`);
      }
```

* 注意

上面调用的地方，笔者是写在`platform.ready().then()`里面的，如果是直接写在构造方法中，会出现`cordova not defined`错误.这里比较坑，后面内部方法再使用的时候，就不用再判断了

完整`ts`代码如下

```
import {Injectable} from "@angular/core";
import {Platform} from "ionic-angular";
import {AppVersion} from "ionic-native";
declare var cordova: any;

@Injectable()
export class LogService{

  constructor(private platform:Platform){
    console.log(`LogService初始化......................`);
    this.platform.ready().then(()=>{
      console.log(`平台初始化完成:${(typeof cordova != 'undefined')},${(typeof cordova)}`);
      if(typeof cordova != 'undefined') {
        cordova.plugins.LogUtil.init('mmmmm', 'log.log', success => {
          console.log(`初始化成功:${JSON.stringify(success)}`);
        }, error => {
          console.log(`初始化失败:${JSON.stringify(error)}`);
        });
      }else{
        console.log(`平台初始化完成之后，cordova插件为undefined`);
      }
    });
  }


  init(){
    try{
      if(cordova){
        try{
          cordova.plugins.LogUtil.init('mmmmm','log.log',success=>{
            console.log(`初始化成功:${JSON.stringify(success)}`);
          },error=>{
            console.log(`初始化失败:${JSON.stringify(error)}`);
          });
        }catch (e){
          console.log(`调用日志插件报错:${JSON.stringify(e)}`);
        }
      }else{
        console.log(`cordova不可用`);
      }
    }catch(e){
      console.log(`初始化出现异常:${JSON.stringify(e.message)}`);
    }

  }

  debug(tag,info){
    if(cordova){
      try{
        cordova.plugins.LogUtil.debug(tag,info);
      }catch (e){
        console.log(`调用日志插件报错:${JSON.stringify(e)}`);
      }
    }else{
      console.log(`cordova不可用`);
    }
  }

  info(tag,info){
    if(cordova){
      try{
        cordova.plugins.LogUtil.info(tag,info);
      }catch (e){
        console.log(`调用日志插件报错:${JSON.stringify(e)}`);
      }
    }else{
      console.log(`cordova不可用`);
    }
  }

  warn(tag,info){
    if(cordova){
      try{
        cordova.plugins.LogUtil.warn(tag,info);
      }catch (e){
        console.log(`调用日志插件报错:${JSON.stringify(e)}`);
      }
    }else{
      console.log(`cordova不可用`);
    }
  }

  error(tag,info){
    if(cordova){
      try{
        cordova.plugins.LogUtil.error(tag,info);
      }catch (e){
        console.log(`调用日志插件报错:${JSON.stringify(e)}`);
      }
    }else{
      console.log(`cordova不可用`);
    }
  }
}
```