
lazy val commonSettings = Seq(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  )
)

val scalajsDomVersion   = "1.0.0"
val scalajsReactVersion = "1.7.7"
val diodeVersion        = "1.1.13"
val akkaHttpVersion     = "2.7.0"
val elastic4sVersion    = "8.5.3"
val circeVersion        = "0.13.0"

lazy val client = (project in file("client"))
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(commonSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js"                      %%% "scalajs-dom"   % scalajsDomVersion,
      "io.suzaku"                         %%% "diode-core"    % diodeVersion,
      "io.suzaku"                         %%% "diode-react"   % diodeVersion,
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

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka"        %% "akka-http"               % "10.4.0",
      "de.heikoseeberger"        %% "akka-http-circe"         % "1.39.2",
      "com.typesafe.akka"        %% "akka-stream"             % akkaHttpVersion,
      "com.typesafe.akka"        %% "akka-actor-typed"        % akkaHttpVersion,
      "com.typesafe.akka"        %% "akka-slf4j"              % akkaHttpVersion,
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
