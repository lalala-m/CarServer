#!/bin/sh
if [ "$1" = "buildwx" ];then
	cd /home/changsheng/project/project80061/client_home
    cp -r /home/changsheng/preview/miniapp_new/node_modules.tar.gz /home/changsheng/project/project80061/client_home/
    tar -zxf node_modules.tar.gz
    yarn build:mp-weixin
    rm -rf /home/changsheng/project/project80061/client_home/node_modules
    rm -rf /home/changsheng/project/project80061/client_home/node_modules.tar.gz
    echo "打包微信成功"
fi

if [ "$1" = "buildapp" ];then
	cd /home/changsheng/project/project80061/client_home
    cp -r /home/changsheng/preview/miniapp_new/node_modules.tar.gz /home/changsheng/project/project80061/client_home/
    tar -zxf node_modules.tar.gz
    yarn build:app-plus
    rm -rf /home/changsheng/project/project80061/client_home/node_modules
    rm -rf /home/changsheng/project/project80061/client_home/node_modules.tar.gz
    echo "打包app成功"
fi

if [ "$1" = "buildh5" ];then
	cd /home/changsheng/project/project80061/client_home
    cp -r /home/changsheng/preview/miniapp_new/node_modules.tar.gz /home/changsheng/project/project80061/client_home/
    tar -zxf node_modules.tar.gz
    yarn build:h5
    rm -rf /home/changsheng/project/project80061/client_home/node_modules
    rm -rf /home/changsheng/project/project80061/client_home/node_modules.tar.gz
    echo "打包h5成功"
fi
