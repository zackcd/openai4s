import CompilerOps._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "typelevel-openai",
    scalacOptions := scalacOps
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.9.0",
  "org.typelevel" %% "cats-effect" % "3.4.6",
  "org.http4s" %% "http4s-ember-client" % "0.23.18",
  "org.http4s" %% "http4s-circe" % "0.23.18"
)

val circeVersion = "0.15.0-M1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)


libraryDependencies += "io.circe" %% "circe-derivation" % "0.13.0-M5"

libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"