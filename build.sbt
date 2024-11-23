ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "tetris",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",

    // Настройки для sbt-assembly
    // Update deprecated settings for sbt-assembly
    assembly / assemblyJarName := "tetris-game.jar",  // JAR file name
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _ => MergeStrategy.first
    }


  )
