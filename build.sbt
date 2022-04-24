//PlayEbean plugin causes runtime failure
lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .settings(
    name := """play-java-starter-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.8",

    resolvers ++= Seq(
      "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
      "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
    ),


      //    libraryDependencies ++= evolutions,

    libraryDependencies ++= Seq(
      //to enable db plugin, add build dependencies
      evolutions,
      javaJdbc,
      //add db driver as application dependency
      "mysql" % "mysql-connector-java" % "8.0.28",
    ),

    libraryDependencies ++= Seq(
      guice,
      // Test Database
      "com.h2database" % "h2" % "1.4.199",
      // Testing libraries for dealing with CompletionStage...
      "org.assertj" % "assertj-core" % "3.14.0" % Test,
      "org.awaitility" % "awaitility" % "4.0.1" % Test,
    ),
    javacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-parameters",
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    ),
    // Make verbose tests
    (Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
  )