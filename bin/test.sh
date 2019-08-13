#!/bin/sh

JAVA=java
JAVAC=java
CLASS_PATH=lib/junit-4.11.jar:bin
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAVA='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/java'
    JAVAC='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/javac'
    CLASS_PATH="bin;lib\junit-4.12.jar;lib\hamcrest-core-1.3.jar;src\test"
fi

"$JAVAC" -cp $CLASS_PATH \
    src/test/StringInitializerTest.java \
	src/test/FeatureTest.java  \
    src/test/FeatureSetTest.java

"$JAVA" -cp $CLASS_PATH org.junit.runner.JUnitCore \
    FeatureSetTest \
    StringInitializerTest \
	FeatureTest
