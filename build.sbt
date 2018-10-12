name := "akka-http-workshop"
organization := "io.github.jlprat"
version := "1.1.0"
scalaVersion := "2.12.7"

lazy val akkaHttpVersion = "10.1.5"
lazy val akkaVersion = "2.5.17"

libraryDependencies ++= Seq(

  // akka http
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-jackson"    % akkaHttpVersion,

  // akka
  "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
  "com.typesafe.akka" %% "akka-actor"           % akkaVersion,

  // ftp
  "commons-net"        % "commons-net"          % "3.6",

  // testing
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % "test",
  "org.scalatest"     %% "scalatest"            % "3.0.5"     % "test",
  "junit"              % "junit"                % "4.12"      % "test",
  "com.novocode"       % "junit-interface"      % "0.11"      % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
