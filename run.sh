#!/bin/sh
APP_DIR=$(pwd)
Xvfb :1 -screen 0 1024x768x24 &
DISPLAY=:1 java -jar tagcloud.jar
killall -9 Xvfb