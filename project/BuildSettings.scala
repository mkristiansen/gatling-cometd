import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
    organization := "org.kaloz.gatling",
    organizationHomepage := Some(new URL("http://waytothepiratecove.blogspot.co.uk")),
    description := "Gatling cometD extension",
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
    scalaVersion := "2.11.4",
    crossPaths := false,
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.7",
      "-language:postfixOps",
      "-language:implicitConversions"
    ),
    javacOptions := Seq(
      "-Xlint:deprecation",
      "-encoding",
      "utf8",
      "-XX:MaxPermSize=256M"
    )
  ) ++ Publish.settings ++ Release.settings

  lazy val noPublishing = Seq(
    publish :=(),
    publishLocal :=()
  )
}