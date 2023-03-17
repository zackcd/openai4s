import CompilerOps._

ThisBuild / name := "openai4s"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

ThisBuild / licenses += "MIT" -> url("https://opensource.org/licenses/MIT")

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

publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  sys.env.getOrElse("SONATYPE_USERNAME", ""),
  sys.env.getOrElse("SONATYPE_PASSWORD", "")
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

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
