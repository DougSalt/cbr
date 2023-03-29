# WARNING

As per usual Java is borked and broken. If you use above version 8 then this
will not work, and I have not had time to fix it yet.

To fix on OSX

```
brew tap homebrew/cask-versions
brew install --cask temurin8
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
```

# BUILD

Then run the remainder of this document.

From the git source directory.

1. ``bin/compile.sh``
2. ``bin/build.sh``
3. ``cp test/cbr/cbr.jar`` to a sub-directory `cbr` directory wherever your model is.

Assumptions: you are sane and running this on one of the following:

1. Linux
2. Cygwin on Windows
3. Linux subsystem on Windows.
4. Mac OSX

In theory I could do all this with `.bat` files, but will only do so, if people
really nag me about it.

If OSX then you need Java8 installed. I would recommend this using the "brew"
system, if done in this manner then

```
brew list
```

You are looking for the following casks (incidentally the difference between a
"cask" and a "formulae" is that casks are pre compiled.

```
adoptopenjdk8
```

To get Java 8 to run, then:
```
export JAVA_HOME=$(/usr/libexec/java_home -v1.8)
```

Remembering unless this you this in one the `*profile` or the `*rc` files then
this will only apply to the current shell.

Have I mentioned anywhere else how much I absolutely detest and loathe Java. I
have managed to compile this, but I have sorted out the dependencies are for
creating the NetLogo plugin, and they are few. In Scala you can build a big jar
- apparently not easily in Java. However because the dependencies in this are
trivial, i.e. only the NetLogo library, then the cbr.jar is all that is needed.


## Typical development workflow.

1. Do you stuff in `src/main`, with testing in `src/test`
2. `bin/compile.sh`
3. `bin/build.sh` (does the release of the jar to the correct directory for
   testing).
4. `bin/run.sh`

Rinse and repeat

All the above can be done with: `bin/all.sh`

To clean, then `bin/clean.sh`

I have abandoned the usual conventions of of Java packaging, as NetLogo does
not tend to work that way, being Scala based.


