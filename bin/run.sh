#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd ~/git/cbr/model
#    /cygdrive/c/Program\ Files/NetLogo\ 6.1.0/NetLogo cabernet.nlogo
    /cygdrive/c/Program\ Files/NetLogo\ 6.1.0/NetLogo
else
    ~/NetLogo-6.1.0/NetLogo $(pwd)/model/cabernet.nlogo
fi
