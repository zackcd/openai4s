import sbt._

object Dependencies {

  val circeVersion = "0.15.0-M1"
  val circeDerivationVersion = "0.13.0-M5"
  val mUnitVersion = "0.7.29"
  val sttpVersion = "3.8.13"
  val logbackClassicVersion = "1.4.6"
  val scalaLoggingVersion = "3.9.5"

  val circeCore = "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val circeParser = "io.circe" %% "circe-parser" % circeVersion
  val circeDerivation = "io.circe" %% "circe-derivation" % circeDerivationVersion
  val mUnitTest = "org.scalameta" %% "munit" % mUnitVersion % Test
  val mUnitIntegrationTest = "org.scalameta" %% "munit" % mUnitVersion % IntegrationTest
  val sttpCore = "com.softwaremill.sttp.client3" %% "core" % sttpVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackClassicVersion
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion

}