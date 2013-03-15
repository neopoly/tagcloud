#!/bin/sh
#
# A shell script to use a tagcloud.jar on headless machines.
# Starts a Window Virtual Framebuffer for the run and kills it afterwards.
# Machines with a frame buffer can use the following command directly
# java -jar tagcloud.jar
#
APP_DIR=$(pwd)
Xvfb :1 -screen 0 1024x768x24 &
DISPLAY=:1 java -jar tagcloud.jar
killall -9 Xvfb