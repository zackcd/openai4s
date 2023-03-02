import CompilerOps._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "openai4s",
    scalacOptions := scalacOps
  )

libraryDependencies ++= {
  val circeVersion = "0.15.0-M1"

  Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion) ++
    Seq(
      "io.circe" %% "circe-derivation" % "0.13.0-M5",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "com.softwaremill.sttp.client3" %% "core" % "3.8.12"
    )
}
