#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd /cygdrive/c/Program\ Files/NetLogo\ 6.2.2/
    ./NetLogo "\\users\\ds42723\\git\\cbr\\example\\example.nlogo"
elif [ $(uname) = "Darwin" ]
then
    ~/netlogo/NetLogo\ 6.2.2/netlogo-gui.sh $(pwd)/example/example.nlogo
else
    ~/.netlogo/NetLogo\ 6.2.2/NetLogo $(pwd)/example/example.nlogo
fi

