package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor._

import scala.util.{Failure, Success}

/**
  * This trait holds all routes in charge of listing the catalog
  */
trait CatalogRoutes extends Directives with JsonProtocol {

  val catalogRoutes: Route = ???
}
