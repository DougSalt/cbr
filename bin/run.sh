#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd /cygdrive/c/Program\ Files/NetLogo\ 6.1.0/
    ./NetLogo "\\users\\ds42723\\git\\cbr\\test\\test.nlogo"
else
    ~/NetLogo-6.1.0/NetLogo $(pwd)/test/test.nlogo
fi
