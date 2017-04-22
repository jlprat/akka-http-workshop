package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.pattern.ask
import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor._

import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * This trait holds all routes in charge of listing the catalog
  */
trait CatalogRoutes extends Directives with JsonProtocol {

  val catalogActorRef: ActorRef

  implicit val timeout: Timeout = 300.millis

  val catalogRoutes: Route = pathPrefix("catalog") {
    (get & pathEndOrSingleSlash) {
      val eventualCatalog = (catalogActorRef ? ListCatalog).mapTo[Catalog]
      complete(eventualCatalog)
    } ~
      (get & path(Segment)) { isbn =>
        val eventualBookInfo = catalogActorRef ? QueryBook(isbn)
        onComplete(eventualBookInfo) {
          case Success(BookInfo(book)) => complete(book)
          case Success(Error(reason)) => complete(StatusCodes.NotFound, reason)
          case Failure(ex) => failWith(ex)
        }
      }
  }
}
