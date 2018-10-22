package io.github.jlprat.akka.http.workshop.parameter

import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * This class showcases some useful parameter directives and matchers
  * Created by @jlprat on 20/04/2017.
  */
class ParameterExample extends HttpApp {
  override protected[parameter] def routes: Route = ???
}

object ParameterExample extends App {
  new ParameterExample().startServer("localhost", 9000)
}