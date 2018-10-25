package io.github.jlprat.akka.http.workshop.path

import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * This class showcases some useful Path directives
  * Created by @jlprat on 19/04/2017.
  */
class PathExample extends HttpApp {
  override protected[path] def routes: Route =
    concat(
      pathPrefix("foo") {
        concat(
          pathEndOrSingleSlash {
            complete("got foo")
          },
          path("bar") {
            complete("got bar")
          })
      },
      get {
        path("other") {
          complete("OK")
        }
      })
}

object PathExample extends App {
  new PathExample().startServer("localhost", 9000)
}
