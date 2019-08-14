lazy val cbr = (project in file("."))
  .settings(
    name := "cbr",
    scalaVersion := "2.12.6",
    packageOptions in (Compile, packageBin) +=  {
      val file = new java.io.File("manifest.txt")
      val manifest = Using.fileInputStream(file)( in => new java.util.jar.Manifest(in) )
      Package.JarManifest( manifest )
<<<<<<< HEAD:old-stuff/build.sbt
=======
    },
    artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
        artifact.name + "." + artifact.extension
>>>>>>> 7aa67a24a227606fda990dc6e7d7188793755846:build.sbt
    }
  )

lazy val release = taskKey[Unit]("Execute release script")
release:= {
    val os = {"uname" !!}.stripLineEnd
    println("OS = " + os)
    if ( os == "Linux" ) {
      "./release.sh" !
    } else {
      "./release.bat" !
    }
}

lazy val netlogo = taskKey[Unit]("Execute nlogo")
netlogo := {
    val os = {"uname" !!}.stripLineEnd
    println("OS = " + os)
    if ( os == "Linux" ) {
      "./run.sh" !
    } else {
      "./run.bat" !
    }
}

