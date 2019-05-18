import sbt._

object Dependencies {

  lazy val akkaHttpVersion: String = "10.1.8"
  lazy val akkaVersion: String = "2.5.22"
  lazy val logbackClassicVersion: String = "1.2.3"
  lazy val scalatestVersion: String = "3.0.7"
  lazy val slf4jVersion = "1.7.26"
  lazy val sprayJsonVersion: String = "1.3.5"

  lazy val akkaActor: ModuleID = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  lazy val akkaHttpSprayJson: ModuleID =
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  lazy val akkaHttpTestkit: ModuleID =
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
  lazy val akkaSlf4j: ModuleID =
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion exclude ("org.slf4j", "slf4j-api")
  lazy val akkaStream: ModuleID = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaStreamTestkit: ModuleID =
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test
  lazy val logbackClassic: ModuleID =
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion exclude ("org.slf4j", "slf4j-api")
  lazy val scalatest: ModuleID =
    "org.scalatest" %% "scalatest" % scalatestVersion % Test
  lazy val slf4j: ModuleID = "org.slf4j" % "slf4j-api" % slf4jVersion
  lazy val sprayJson: ModuleID = "io.spray" %% "spray-json" % sprayJsonVersion
  lazy val typesafeConfig: ModuleID =  "com.typesafe" % "config" % "1.3.4"


  def scalaCompiler(scalaVersion: String): ModuleID =
    "org.scala-lang" % "scala-compiler" % scalaVersion
  def scalaLibrary(scalaVersion: String): ModuleID =
    "org.scala-lang" % "scala-library" % scalaVersion
  def scalaReflect(scalaVersion: String): ModuleID =
    "org.scala-lang" % "scala-reflect" % scalaVersion

  def defaultDependencies(scalaVersion: String) = Seq(
    scalaCompiler(scalaVersion),
    scalaLibrary(scalaVersion),
    scalaReflect(scalaVersion),
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
    sprayJson,
    typesafeConfig
  )
}
