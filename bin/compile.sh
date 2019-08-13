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
    src/main/Case.java \
    src/main/CaseBase.java \
    src/main/CaseBaseException.java \
    src/main/ComparableFeature.java \
    src/main/ComparableFeatureValue.java \
    src/main/Feature.java \
    src/main/FeatureSet.java \
    src/main/POCmp.java \
    src/main/StringFeature.java \
    src/main/StringFeatureValue.java \
    src/main/StringInitializer.java

"$JAVAC" -d bin -cp $CLASS_PATH \
    src/main/AddCase.java \
    src/main/AddCaseBase.java \
    src/main/AllCases.java \
    src/main/CBRExtension.java \
    src/main/CaseLambda.java \
    src/main/Closer.java \
    src/main/Comparable.java \
    src/main/Decision.java \
    src/main/Equal.java \
    src/main/Forget.java \
    src/main/GetEarliest.java \
    src/main/GetMaxSize.java \
    src/main/GetRank.java \
    src/main/GetTime.java \
    src/main/Invalid.java \
    src/main/Match.java \
    src/main/NetLogoCase.java \
    src/main/NetLogoCaseBase.java \
    src/main/NetLogoFeature.java \
    src/main/NetLogoFeatureSet.java \
    src/main/NetLogoFeatureValue.java \
    src/main/NetLogoFeatureValueSet.java \
    src/main/New.java \
    src/main/No.java \
    src/main/Outcome.java \
    src/main/Remove.java \
    src/main/Resize.java \
    src/main/Same.java \
    src/main/SetEarliest.java \
    src/main/SetMaxSize.java \
    src/main/SetRank.java \
    src/main/SetTime.java \
    src/main/State.java \
    src/main/Yes.java 

#    src/main/FromList.java \
