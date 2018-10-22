package io.github.jlprat.akka.http.workshop.ex4

import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * This class showcases how to deal with blocking
  * Created by @jlprat on 21/04/2017.
  */
class BlockingExample extends HttpApp {

  override protected def routes: Route = ???
}

object BlockingExample extends App {
  new BlockingExample().startServer("localhost", 9000)
}