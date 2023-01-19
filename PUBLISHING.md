See [here](https://github.com/NetLogo/NetLogo-Libraries#netlogo-libraries) for iinstructions.

Fork the repository ihttps://github.com/NetLogo/NetLogo-Libraries in github.com. This is done by clicking the fork button in the right hand corner of the screen. Then clone this repository

```
git clone https://github.com/DougSalt/NetLogo-Libraries/compare
```

make a new branch

```
cd NetLogo-Libraries
git checkout -b cbr
git remote add upstream https://NetLogo/NetLogo-Libraries
```

copy the latest version of test/cbr/cbr.jar into the correct zip format
by using the command

```
cd ~/git/cbr/test/cbr
zip cbr-1.0.1.jar cbr.jar
cp cbr.jar ~/git/NetLogo-Libraries/extensions
```

where `1.0.1` is the version number

Add this file to `NetLogo-Libraries/extensions` directory.

Add the following to the `NetLogo-Libraries/libraries.conf`


{
    name: "Case based reasoning"
    codeName: "cbr"
    shortDescription: "A case based reasoner extension"
    longDescription: """A case base reasoner that provides the necessary
keywords to implement a case based reasoner consistently. A case base consists
of the riple: state,decision,outcome. This framework can return single or
multiple triples from the case base which match a given state and decision
using the the inbuilt distance function or a user defined function. Command to dynamically modify the case base are also provided, such as addition and deletion of specific cases based on age, or size of case base.
"""
    version: "1.0.1"
    homepage: "https://github.com/DougSalt/cbr.git"
    downloadURL: "https://raw.githubusercontent.com/NetLogo/NetLogo-Libraries/6.1/extensions/cbr-1.0.1.zip"
}

then

```
git add extensions/cbr.jar
git commit -m "Adding the CBR extension."
git push
```

The go up to https://github.com/NetLogo/NetLogo-Libraries/
and press the button to do the pull and merge request.

