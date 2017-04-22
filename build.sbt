name := "akka-http-workshop"
organization := "io.github.jlprat"
version := "1.0.0"
scalaVersion := "2.12.2"

lazy val akkaHttpVersion = "10.0.5"

libraryDependencies ++= Seq(

  // akka http
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  // ftp
  "commons-net"        % "commons-net"          % "3.6",

  // testing
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % "test",
  "org.scalatest"     %% "scalatest"            % "3.0.1"     % "test",
  "junit"              % "junit"                % "4.12"      % "test",
  "com.novocode"       % "junit-interface"      % "0.11"      % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
