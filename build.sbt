import CompilerOptions._
import Dependencies._

lazy val `root` = (project in file(""))
  .settings(
    organization := "rws",

    name := "quant",

    version := "0.0.1-SNAPSHOT",

    scalaVersion := "2.12.8",

    scalacOptions := scalacOptionsVersion(scalaVersion.value),

    scalacOptions in (Compile, console) := scalacConsoleOptionsVersion(
      scalaVersion.value
    ),

    libraryDependencies ++= Seq(
      scalaCompiler(scalaVersion.value),
      scalaLibrary(scalaVersion.value),
      scalaReflect(scalaVersion.value),
      akkaActor,
      akkaHttp,
      akkaHttpSprayJson,
      akkaHttpTestkit,
      akkaSlf4j,
      akkaStream,
      akkaStreamTestkit,
      logbackClassic,
      scalatest,
      slf4j,
      sprayJson
    )
  )
