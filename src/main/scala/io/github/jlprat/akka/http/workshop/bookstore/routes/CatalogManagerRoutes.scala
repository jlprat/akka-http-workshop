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

  val catalogActorRef: ActorRef

  implicit val timeout: Timeout

  private def authorize(username: String) = {
    username.equalsIgnoreCase("admin")
  }

  val catalogManagerRoutes: Route = (pathPrefix("catalog" / "admin" / "book") &
    pathEndOrSingleSlash &
    (put | delete)) { //those are needed to provide the right feedback when no credentials are provided
    authenticateBasic("book-realm", myUserPassAuthenticator) { username =>
      authorize(authorize(username)) {
        entity(as[Book]) { book =>
          extractLog { log =>
            put {
              val result = catalogActorRef ? AddBook(book)
              onComplete(result) {
                case Success(CatalogActor.Success) => complete("OK")
                case Failure(ex) => failWith(ex)
              }
            } ~
              delete {
                val result = catalogActorRef ? RemoveBook(book)
                onComplete(result) {
                  case Success(CatalogActor.Success) => complete("OK")
                  case Success(CatalogActor.Error(msg)) =>
                    log.info(s"Book ($book) was already removed!")
                    complete("OK")
                  case Failure(ex) => failWith(ex)
                }
              }
          }
        }
      }
    }
  }
}