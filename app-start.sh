#!/bin/bash

if [ "$LIFECYCLE_EVENT" == "ApplicationStart" ]; then
    sudo docker run --platform linux/amd64 -p8001:8001 shahen221/adminskillapiaws:latest 
    exit
fi