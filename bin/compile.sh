#!/bin/sh

# Doug - August 2019

# A script to compile the cbr plugin. I probably should use ant to do this,
# I am unaware of how the $JAVAC treats dependencies and cannot be bothered
# to find out. I have also taken this opportunity to clear out the lib
# directory, because generally people cannot be bothered working out which
# libraries they need and just bung in the last set that work - hence the
# massive list of libraries in lib/old

# Now this is really weird. You generally need to clean out the class directory
# because ComparableFeature.java uses Comparable and a template, and if this 
# is already compiled as a class, then it objects. How crap is that?

# Note that I am using a named removed in the clean, because there might be
# other stuff that this shouldn't clean - I am talking to you eclipse IDE.

JAVAC=javac
CLASS_PATH=lib/netlogo-6.1.0.jar:bin:lib/scala-library.jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAVAC='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/javac'
    CLASS_PATH="bin;lib\netlogo-6.1.0.jar;lib\scala-library.jar"
fi

if [ -n "$1" ]
then
    "$JAVAC" -d bin -cp $CLASS_PATH "$1"
    exit
fi

bin/clean.sh 2>/dev/null

"$JAVAC" -d bin -cp $CLASS_PATH \
    src/Case.java \
    src/CaseBase.java \
    src/CaseBaseException.java \
    src/AddCase.java \
    src/CombineCaseBases.java \
    src/AllCases.java \
    src/CBRExtension.java \
    src/CaseLambda.java \
    src/Closer.java \
    src/Comparable.java \
    src/Decision.java \
    src/Equal.java \
    src/Forget.java \
    src/GetEarliest.java \
    src/GetMaxSize.java \
    src/GetRank.java \
    src/GetTime.java \
    src/Invalid.java \
    src/Match.java \
    src/NetLogoCase.java \
    src/NetLogoCaseBase.java \
    src/New.java \
    src/No.java \
    src/Outcome.java \
    src/Remove.java \
    src/Resize.java \
    src/Same.java \
    src/SetEarliest.java \
    src/SetMaxSize.java \
    src/SetRank.java \
    src/SetTime.java \
    src/State.java \
    src/Yes.java
