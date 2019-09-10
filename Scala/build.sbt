javaSource in Compile := baseDirectory.value / "src"
javaSource in Test := baseDirectory.value / "test"

lazy val life = (project in file("."))
  .settings(
    name := "Conway's Game of Life",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,	
  )
