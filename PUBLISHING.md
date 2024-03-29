See [here](https://github.com/NetLogo/NetLogo-Libraries#netlogo-libraries) for iinstructions.

Fork the repository ihttps://github.com/NetLogo/NetLogo-Libraries in github.com. This is done by clicking the fork button in the right hand corner of the screen. Then clone this repository

```
git clone git@github.com:/DougSalt/NetLogo-Libraries
```
or if you have used this before then on the main page of github for that
project, switch to the 6.1 branch and pull to the state of the master.
You need to update your local version of NetLogo-libraries, locally:

```
git pull upstream 6.1
```

Make a new branch

```
cd NetLogo-Libraries
git checkout -b cbr
# I don't think you need to do this line.
git remote add upstream git@github.com:NetLogo/NetLogo-Libraries.git
```

copy the latest version of test/cbr/cbr.jar into the correct zip format
by using the command

```
cd ~/git/cbr/test/cbr
zip cbr-1.0.2.zip cbr.jar
cp cbr-1.0.2.zip  ~/git/NetLogo-Libraries/extensions
```

where `1.0.2` is the version number

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
    version: "1.0.2"
    homepage: "https://github.com/DougSalt/cbr.git"
    downloadURL: "https://raw.githubusercontent.com/NetLogo/NetLogo-Libraries/6.1/extensions/cbr-1.0.2.zip"
}

then

```
git add extensions/cbr.jar
git commit -a -m "Adding the CBR extension."
git push
```

The go up to https://github.com/DougSalt/NetLogo and generate a pull request back to the original repository.

