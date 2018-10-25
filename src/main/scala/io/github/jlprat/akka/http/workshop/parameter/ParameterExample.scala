package io.github.jlprat.akka.http.workshop.parameter

import akka.http.scaladsl.server.{HttpApp, Route}

import scala.language.postfixOps

/**
  * This class showcases some useful parameter directives and matchers
  * Created by @jlprat on 20/04/2017.
  */
class ParameterExample extends HttpApp {
  override protected[parameter] def routes: Route = concat(
    path("listen") {
      parameter("p1") { p1 =>
        parameter("p2") { p2 =>
          complete(s"p1 -> $p1, p2 -> $p2")
        }
      }
    },
    path("opt") {
      parameter("p1" ?) { p1 =>
        complete(s"p1 -> ${p1.getOrElse("unknown")}")
      }
    },
    path("buy" / Segment) { thing =>
      complete(s"You want to buy a $thing")
    },
    path("double" / IntNumber) { number =>
      complete(s"${number * 2}")
    })

}

object ParameterExample extends App {
  new ParameterExample().startServer("localhost", 9000)
}