package io.github.jlprat.akka.http.workshop.bookstore

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.settings.ServerSettings
import io.github.jlprat.akka.http.workshop.bookstore.actor.{CatalogActor, ReviewerActor}

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

class BookShopHttp(catalogActorRef: ActorRef, reviewerActorRef: ActorRef) extends HttpApp {
  override protected def route: Route = ???
}