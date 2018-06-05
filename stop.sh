#!/bin/sh

pid=$(cat /usr/local/source_code/kettle/pid/kettle.pid)
echo "pid[$pid] will be killed"
kill -9 $pid
echo "Done"
