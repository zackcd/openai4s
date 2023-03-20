import CompilerOps._
import Dependencies._

inThisBuild(
  List(
    name := "openai4s",
    scalaVersion := "2.13.10",
    organization := "io.github.zackcd",
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
  )
)

ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(integrationTestSettings)
  .settings(
    name := "openai4s",
    scalacOptions := scalacOps
  )
  .settings(
    libraryDependencies ++= Seq(
      circeCore,
      circeGeneric,
      circeParser,
      circeDerivation,
      mUnitTest,
      mUnitIntegrationTest,
      sttpCore,
      logbackClassic,
      scalaLogging
    )
  )

lazy val integrationTestSettings = Seq(
  IntegrationTest / parallelExecution := false,
  IntegrationTest / fork := true,
  IntegrationTest / publishArtifact := false
)
