package io.github.jlprat.akka.http.workshop.actors

import akka.actor.Scheduler
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, HttpApp, Route}
import akka.util.Timeout
import akka.{actor => untyped}
import io.github.jlprat.akka.http.workshop.actors.CatalogBehavior._
import io.github.jlprat.akka.http.workshop.actors.CatalogRouteExample.CatalogRoute
import io.github.jlprat.akka.http.workshop.actors.Model.{Author, Book}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * This class showcases how to integrate with Akka Typed
  * Created by @jlprat on 24/04/2017.
  */
class CatalogRouteExample(override val catalogBehavior: ActorRef[Command]) extends HttpApp with CatalogRoute {
  override protected[actors] def routes: Route = catalogRoute
}

object CatalogRouteExample {


  trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val authorFormat: RootJsonFormat[Author] = jsonFormat1(Author)
    implicit val bookFormat: RootJsonFormat[Book] = jsonFormat4(Book)
  }

  trait CatalogRoute extends Directives with JsonProtocol {

    val catalogBehavior: ActorRef[CatalogBehavior.Command]

    val catalogRoute: Route = put {
      path("catalog" / "book" / IntNumber) { quantity =>
        entity(as[Book]) { book =>
          extractActorSystem { system =>

            implicit val timeout: Timeout = 3.seconds
            implicit val scheduler: Scheduler = system.toTyped.scheduler

            val result: Future[CatalogReply] = catalogBehavior ? (ref => AddBooks(book, quantity, ref))
            onComplete(result) {
              case Success(OperationPerformed) => complete("OK")
              case Success(OperationFailed) => complete((StatusCodes.BadRequest, "Too many books to print"))
              case Failure(exception) => failWith(exception)
            }
          }

        }
      }
    }
  }


  def main(args: Array[String]): Unit = {
    val system = untyped.ActorSystem("foo")
    val catalogBehavior = system.spawn(CatalogBehavior.catalogBehavior, "catalogActor")

    new CatalogRouteExample(catalogBehavior).startServer("localhost", 9000, system)
  }
}
