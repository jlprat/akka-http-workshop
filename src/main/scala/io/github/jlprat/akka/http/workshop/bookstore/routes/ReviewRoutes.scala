package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.server.{Directives, Route}

/**
  * This trait holds all routes in charge of reviews
  */
trait ReviewRoutes extends Directives with JsonProtocol with Authentication {

  val reviewRoutes: Route = ???

}
