# PURPOSE

This contains stuff that is compiled or runs.

# MANIFEST

+ \*.class - the compiled Java classes
+ README.md - this file
+ all.sh - runs `compile.sh`, `build.sh` and `run.sh` in sequence.
+ build.sh - builds the object into a jar and moves the resultant jar to the testing target file `test/cbr/cbr.jar'
+ clean.sh - deletes all classes explicitly. Done this way because Java IDEs have a nasty tendency to assume they own a directory completely and tend to over clean (i.e. delete everything in that directory).
+ compile.sh - compiles the Java code
+ osx.sh - sets up the environment to Java 8 on the Mac. This needs to be sourced from a shell.
+ run.sh - runs the test/reference implementation of cbr. The files for this are found in `test`
