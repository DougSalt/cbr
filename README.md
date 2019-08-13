# Purpose

## cabernet

A NetLogo case based reasoning toolkit as a NetLogo plugin.

Have I mentioned anywhere else how much I absolutely detest and loathe Java. I
have managed to compile this, but I have absolutely no idea what the
dependencies are for creating the NetLogo plugin. I think I am just going to
have to copy the entire `lib` directory. In Scala you can build a big jar -
apparently not easily in Java.
to me.

To use this in the NetLogo plugin `cbr`

# Typical workflow

1. Do you stuff in `src/main`, with testing in `src/test`
2. `bin/compile.sh`
3. `bin/build.sh` (does the release of the jar to the correct directory for
   testing).
4. `bin/test.sh` - not very comprehensive yet - I will write some more.
4. `bin/run.sh`

All the above can be done with: `bin\all.sh`

To clean, then `bin/clean.sh`

I have abandoned the usual conventions of of Java packaging, as NetLogo does
not tend to work that way, being Scala based.

# Contacts

doug[dot]salt[at]hutton[dot]ac[dot]uk
