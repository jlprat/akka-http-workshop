package io.github.jlprat.akka.http.workshop.unconsumed

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * This class showcases how dangerous is to not consume existing streams
  * Created by @jlprat on 24/04/2017.
  */
object UnconsumedStreams extends App {

  implicit val system: ActorSystem = ActorSystem("foo")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val ec = system.dispatcher
  val requests = (1 to 35).map(i => {
    val eventualResponse = issueRequest(s"http://neverssl.com")
    println(s"query $i")
//    Thread.sleep(20)
    eventualResponse.flatMap(response => {
      println(s"received response $i - ${response.status}")
      Future.successful(Done)
    })
  })

  Future.sequence(requests).onComplete{
    case Success(_) =>
      println("All good!")
      system.terminate()
    case Failure(ex) =>
      Console.err.println(s"Something went wrong - $ex")
      system.terminate()
  }

  def issueRequest(url: String): Future[HttpResponse] = {
    Http().singleRequest(HttpRequest(uri = url))
  }
}
