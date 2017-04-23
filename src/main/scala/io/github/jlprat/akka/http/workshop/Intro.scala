package io.github.jlprat.akka.http.workshop

import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * Short server using [[HttpApp]] to help getting started
  * Created by @jlprat on 15/04/2017.
  */
class Intro extends HttpApp {
  override protected[workshop] def route: Route = path("hello") {
    complete("world!")
  }
}

object Intro extends App {
  new Intro().startServer("localhost", 9000)
}