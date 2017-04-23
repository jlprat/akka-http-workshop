package io.github.jlprat.akka.http.workshop.bookstore

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.settings.ServerSettings
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.{CatalogActor, ReviewerActor}
import io.github.jlprat.akka.http.workshop.bookstore.routes.{CatalogManagerRoutes, CatalogRoutes}

import scala.concurrent.Await
import scala.concurrent.duration._

object BookShopApp extends App {

  val system = ActorSystem("BookShop")
  private val catalogActor = system.actorOf(CatalogActor.props)
  private val reviewerActor = system.actorOf(ReviewerActor.props)
  private val bookShopHttp = new BookShopHttp(catalogActor, reviewerActor)

  bookShopHttp.startServer("localhost", 9000, ServerSettings(system), system)

  Await.result(system.terminate(), 10.seconds)
}

class BookShopHttp(override val catalogActorRef: ActorRef, reviewerActorRef: ActorRef)
  extends HttpApp
    with CatalogRoutes
    with CatalogManagerRoutes {
  override implicit val timeout: Timeout = 300.millis

  override protected def route: Route = catalogRoutes ~ catalogManagerRoutes
}