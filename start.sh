#!/bin/sh

cd /usr/local/source_code/kettle

git pull

mvn clean package install -Dmaven.test.skip=true

nohup java -jar /usr/local/source_code/kettle/target/etl-sync-0.1.0-RELEASE.jar --spring.profiles.active=test >> /usr/local/source_code/kettle/logs/kettle.log 2>&1 &

echo $! > /usr/local/source_code/kettle/pid/kettle.pid

echo Start Success!

tail -f /usr/local/source_code/kettle/logs/kettle.log
