#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd /cygdrive/c/Program\ Files/NetLogo\ 6.3.0/
    ./NetLogo "\\users\\ds42723\\git\\cbr\\test\\cbr.nlogo"
elif [ $(uname) = "Darwin" ]
then
    export JAVA_HOME=$(/usr/libexec/java_home -v19)
    ~/Applications/netlogo/NetLogo\ 6.3.0/netlogo-gui.sh $(pwd)/test/cbr.nlogo
else
    ~/.netlogo/NetLogo\ 6.3.0/NetLogo $(pwd)/test/cbr.nlogo
fi

