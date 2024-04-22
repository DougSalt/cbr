The files you need to change are for an upgrade to NetLogo are:

If N.x.y is the release of NetLogo

+ edit `jar_manifest.txt` - to update the NetLogo extension to N.x
+ copy `netlogo-N.x.y.jar` to `lib` from the Netlogo tree
+ in `bin/build.sh` update the library path to pick up the file just copied into `lib`
+ change `bin/run.sh` to pick up the new version of NetLogo
