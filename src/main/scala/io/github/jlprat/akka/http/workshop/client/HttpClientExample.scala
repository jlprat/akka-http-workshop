package io.github.jlprat.akka.http.workshop.client

import akka.actor.ActorSystem

import scala.concurrent.Future

/**
  * This object showcases the Akka HTTP Client API
  * Created by @jlprat on 22/10/2018.
  */
object HttpClientExample {

  def getPage(uri: String)(implicit system: ActorSystem): Future[String] = ???

}
