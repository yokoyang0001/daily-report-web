name := """daily-report-web"""
organization := "com.yokoyang0001"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayJava, PlayEbean)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  ehcache,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  ws,
  "org.mindrot" % "jbcrypt" % "0.4",
  "mysql" % "mysql-connector-java" % "5.1.36"
)