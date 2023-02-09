
lazy val commonSettings = Seq(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  )
)

val scalajsReactVersion = "1.7.7"

lazy val client = (project in file("client"))
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(commonSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js"                      %%% "scalajs-dom"   % "1.0.0",
      "io.suzaku"                         %%% "diode-core"    % "1.1.13",
      "io.suzaku"                         %%% "diode-react"   % "1.1.13",
      "io.circe"                          %%% "circe-core"    % circeVersion,
      "io.circe"                          %%% "circe-generic" % circeVersion,
      "io.circe"                          %%% "circe-parser"  % circeVersion,
      "com.github.japgolly.scalajs-react" %%% "core"          % scalajsReactVersion,
      "com.github.japgolly.scalajs-react" %%% "extra"         % scalajsReactVersion
    ),
    Compile / npmDependencies ++= Seq("react" -> "16.13.1", "react-dom" -> "16.13.1"),
    (fastOptJS / webpackBundlingMode) := BundlingMode.LibraryAndApplication(),
    Compile / fastOptJS / artifactPath := ((Compile / fastOptJS / crossTarget).value /
    ((fastOptJS / moduleName).value + "-opt.js"))
  )

val akkaVersion         = "2.7.0"
val elastic4sVersion    = "8.5.3"
val circeVersion        = "0.14.4"

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka"        %% "akka-http"               % "10.4.0",
      "de.heikoseeberger"        %% "akka-http-circe"         % "1.39.2",
      "com.typesafe.akka"        %% "akka-stream"             % akkaVersion,
      "com.typesafe.akka"        %% "akka-actor-typed"        % akkaVersion,
      "com.typesafe.akka"        %% "akka-slf4j"              % akkaVersion,
      "ch.qos.logback"           % "logback-classic"          % "1.4.5",
      "io.circe"                 %% "circe-core"              % circeVersion,
      "io.circe"                 %% "circe-generic"           % circeVersion,
      "io.circe"                 %% "circe-parser"            % circeVersion,
      "com.sksamuel.elastic4s"   %% "elastic4s-client-esjava" % elastic4sVersion,
    )
  )

lazy val root = (project in file("."))
  .aggregate(server)
  .aggregate(client)
