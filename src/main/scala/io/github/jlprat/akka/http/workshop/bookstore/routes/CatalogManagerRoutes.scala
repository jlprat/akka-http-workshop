package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.server.{Directives, Route}

/**
  * This trait holds all routes in charge of managing the catalog
  */
trait CatalogManagerRoutes extends Directives with JsonProtocol with Authentication {

  val catalogManagerRoutes: Route = ???
}
