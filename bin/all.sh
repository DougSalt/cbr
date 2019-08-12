#!/bin/sh
if ! bin/compile.sh 
then
    echo "$0: Compilation failed"
    exit 1
fi

if ! bin/build.sh
then
    echo "$0: Build failed"
    exit 1
fi
bin/run.sh
