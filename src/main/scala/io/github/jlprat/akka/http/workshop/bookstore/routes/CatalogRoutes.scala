package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.server.{Directives, Route}

/**
  * This trait holds all routes in charge of listing the catalog
  */
trait CatalogRoutes extends Directives with JsonProtocol {

  val catalogRoutes: Route = ???
}
