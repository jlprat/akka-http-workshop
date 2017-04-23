package io.github.jlprat.akka.http.workshop.ex2

import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * This class showcases some useful parameter directives and matchers
  * Created by @jlprat on 20/04/2017.
  */
class ParameterExample extends HttpApp {
  override protected[ex2] def route: Route = ???
}

object ParameterExample extends App {
  new ParameterExample().startServer("localhost", 9000)
}