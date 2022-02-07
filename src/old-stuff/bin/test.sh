#!/bin/sh

# Doug Salt - August 2019

# This was an attempt to write a testing infrastruce which didn't work, because
# the NetLogo stuff, understandly, requires a lot of initialization. This is
# the YesTest class. The other three classes are for testing Gary's
# implementation of a more generic version of a hashtable or table in NetLogo
# parlance.

JAVA=java
JAVAC=javac
CLASS_PATH=lib/junit-4.12.jar:bin:lib/hamcrest-core-1.3.jar:src/test:lib/netlogo-6.1.0.jar 
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAVA='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/java'
    JAVAC='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/javac'
    CLASS_PATH="bin;lib\junit-4.12.jar;lib\hamcrest-core-1.3.jar;src\test;lib\netlogo-6.1.0.jar"
fi

"$JAVAC" -cp $CLASS_PATH \
    src/test/YesTest.java \
    src/test/StringInitializerTest.java \
	src/test/FeatureTest.java  \
    src/test/FeatureSetTest.java

"$JAVA" -cp $CLASS_PATH org.junit.runner.JUnitCore \
    YesTest
    FeatureSetTest \
    StringInitializerTest \
	FeatureTest
