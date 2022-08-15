ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "otus-1.5"
  )

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.3.2"
