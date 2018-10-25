package io.github.jlprat.akka.http.workshop.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/**
  * This object showcases the Akka HTTP Client API
  * Created by @jlprat on 22/10/2018.
  */
object HttpClientExample {

  def getPage(uri: String)(implicit system: ActorSystem): Future[String] = {

    implicit val materializer: Materializer = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher


    // These are equivalent ways of getting an entity to a String

//    Http().singleRequest(HttpRequest(uri = uri)).flatMap(response =>
//      response.entity.dataBytes.runFold(ByteString.empty)(_ ++ _)
//    ).map(_.utf8String)

    Http().singleRequest(HttpRequest(uri = uri)).flatMap(response =>
      response.entity.toStrict(2.seconds)
    ).map(_.data.utf8String)

  }

}
