import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.tristanmedia"
ThisBuild / organizationName := "experiments"

val akkaV          = "2.5.13"
val akkaHttpV      = "10.1.5"
val slickVersion   = "3.3.0"
val slickPgVersion = "0.17.2"

lazy val root = (project in file("."))
  .settings(
    name := "fusion",
    libraryDependencies ++= Seq(
      "com.typesafe"            % "config" % "1.3.2",
      "org.postgresql"          % "postgresql" % "42.2.4", // For database access
      "org.playframework.anorm" %% "anorm" % "2.6.1", // ..
      "com.typesafe.slick"      %% "slick" % slickVersion, // ..
      "com.typesafe.slick"      %% "slick-hikaricp" % slickVersion, // ..
      "com.github.tminglei"     %% "slick-pg" % slickPgVersion, // ..
      "com.github.tminglei"     %% "slick-pg_spray-json" % slickPgVersion, // ..
      "com.typesafe.akka"       %% "akka-stream" % akkaV, // Actors -- needed for Akka HTTP
      "com.typesafe.akka"       %% "akka-http" % akkaHttpV, // API framework
      "com.typesafe.akka"       %% "akka-http-spray-json" % akkaHttpV, // ..
      scalaTest                 % Test
    )
  )

enablePlugins(FlywayPlugin)

// load config for Flyway
val appConfig = com.typesafe.config.ConfigFactory.parseFile(new File("src/main/resources/application.conf")).resolve()
flywayUrl := appConfig.getString("slick.db.url")
flywayUser := appConfig.getString("slick.db.user")
flywayPassword := appConfig.getString("slick.db.password")
