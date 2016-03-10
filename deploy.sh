#!/bin/sh
echo "git pull start*********"
#git pull
echo "git pull end**********"

#前端目录
PATH_FRONT=front
#要部署的项目
PROJECT_ROOT=sven-admin-web
#要部署的项目的webapp目录
PATH_SRC_ROOT=${PROJECT_ROOT}/src/main/webapp
#要部署的项目maven编译后的war包解压的目录
PATH_TARGET_ROOT=${PROJECT_ROOT}/target/adminbg
#部署的地方
WEB_ROOT=sven-admin-web/target/webroot
#tomcat目录
TOMCAT_ROOT=/home/stefan/programFiles/apache-tomcat-7.0.62

set -e

export PATH

echo "copy front src start*******"

if [ -d "${PATH_SRC_ROOT}/html" ]; then
    printf '%s\n' "remove front dir: $PATH_SRC_ROOT/html/*"
    rm -rf $PATH_SRC_ROOT/html
fi

mkdir ${PATH_SRC_ROOT}/html

cp -rp $PATH_FRONT/* $PATH_SRC_ROOT/html

echo "copy front src end*******"

echo "request path add html/ start*****"

sed -i "s/public\//html\/public\//g" `grep "public\/" -rl ${PATH_SRC_ROOT}/html`
sed -i "s/src\//html\/src\//g" `grep "src\/" -rl ${PATH_SRC_ROOT}/html`

echo "request path add html/ end*****"

echo "maven package start*******"

mvn clean package -U -Dmaven.test.skip=true

echo "maven package end*******"

echo "deploy start****"
mkdir -p ${WEB_ROOT}


cp -fr ${PATH_TARGET_ROOT}/* $WEB_ROOT

#sh $TOMCAT_ROOT/bin/catalina.sh stop
#sh $TOMCAT_ROOT/bin/catalina.sh start

echo "deploy end****"