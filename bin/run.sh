#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd /cygdrive/c/Program\ Files/NetLogo\ 6.2.2/
    ./NetLogo "\\users\\ds42723\\git\\cbr\\test\\cbr.nlogo"
else
    ~/.netlogo/NetLogo\ 6.2.2/NetLogo $(pwd)/test/cbr.nlogo
fi

