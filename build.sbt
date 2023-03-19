import CompilerOps._

ThisBuild / name := "openai4s"
ThisBuild / scalaVersion := "2.13.10"
ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

inThisBuild(List(
  organization := "com.github.zackcd",
  homepage := Some(url("https://github.com/zackcd/openai4s")),
  licenses := List("MIT" -> url("https://opensource.org/licenses/MIT")),
  developers := List(
    Developer(
      "zackcd",
      "Zack Downing",
      "zack_downing@mac.com",
      url("https://githun.com/zackcd")
    )
  )
))

lazy val integrationTestSettings = Seq(
  IntegrationTest / parallelExecution := false,
  IntegrationTest / fork := true,
  IntegrationTest / publishArtifact := false,
  IntegrationTest / envVars := Map(
    "OPENAI_API_KEY" -> sys.env.getOrElse("OPENAI_API_KEY", "test-key")
  )
)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(integrationTestSettings)
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
      "org.scalameta" %% "munit" % "0.7.29" % IntegrationTest,
      "com.softwaremill.sttp.client3" %% "core" % "3.8.12"
    )
}

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5.13",
  "org.apache.httpcomponents" % "httpcore" % "4.4.14"
)


libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
