package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.{AddBook, RemoveBook}
import io.github.jlprat.akka.http.workshop.bookstore.model.Book

import scala.util.{Failure, Success}

/**
  * This trait holds all routes in charge of managing the catalog
  */
trait CatalogManagerRoutes extends Directives with JsonProtocol with Authentication {

  val catalogManagerRoutes: Route = ???
}
