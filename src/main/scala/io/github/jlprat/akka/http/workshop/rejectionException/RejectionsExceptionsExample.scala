package io.github.jlprat.akka.http.workshop.rejectionException

import akka.http.scaladsl.server._

/**
  * This class showcases rejections and exceptions handling
  * Created by @jlprat on 20/04/2017.
  */
class RejectionsExceptionsExample extends HttpApp {

  override protected[rejectionException] def routes: Route = ???
}

object RejectionsExceptionsExample extends App {
  new RejectionsExceptionsExample().startServer("localhost", 9000)
}
