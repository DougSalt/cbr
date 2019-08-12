# Purpose

## cabernet

A 'generic' case based reasoning toolkit as a NetLogo plugin.

Have I mentioned anywhere else how much I aboslutely detest and loathe Java. I
mhave managed to compile this, but I have absolutely no idea what the
dependencies are for creating the netlogo plugin. I think I am just going to
have to copy the entire `lib` directory. In Scala you can build a big jar -
apparently not in Java - you must use maven or gradle, which are not available
to me.

To use this in the NetLogo plugin `cbr`

# Typical worflow

1. Do you stuff in `src`
2. `bin/compile.sh`
3. `bin/build.sh` (does the release of the jar to the correct directory for
   testing).
4. `bin/run.sh`

All the above can be done with: `bin\all.sh`

To clean, then `bin/clean.sh`

# Contacts
