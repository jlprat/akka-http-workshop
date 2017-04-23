package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.pattern.ask
import akka.actor.ActorRef
import akka.http.scaladsl.server.directives.Credentials
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.{AddBook, RemoveBook}
import io.github.jlprat.akka.http.workshop.bookstore.model.Book

import scala.util.{Failure, Success}

trait CatalogManagerRoutes extends Directives with JsonProtocol {

  val catalogActorRef: ActorRef

  implicit val timeout: Timeout

  //dummy auth
  private def myUserPassAuthenticator(credentials: Credentials): Option[String] = {
    credentials match {
      case cred@Credentials.Provided(id) if cred.verify("LetMeIn") => Some(id)
      case _ => None
    }
  }

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
