package io.github.jlprat.akka.http.workshop.ex3

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._

/**
  * This class showcases rejections and exceptions handling
  * Created by @jlprat on 20/04/2017.
  */
class RejectionsExceptionsExample extends HttpApp {
  private val rejectionHandler = RejectionHandler.newBuilder()
    .handle {
      case _: MethodRejection => complete(StatusCodes.MethodNotAllowed, "Have you tried with more conventional methods?")
    }
    .handleNotFound(complete(StatusCodes.NotFound, "Nothing to see here!"))
    .result()

  private val exceptionHandler = ExceptionHandler.apply{
    case _: ArithmeticException => complete(StatusCodes.InternalServerError, "Do you math?")
  }

  override protected[ex3] def route: Route = handleRejections(rejectionHandler) {
    get {
      path("onlyGet") {
        complete("got it")
      }
    } ~
    handleExceptions(exceptionHandler) {
      path("zerodivision") {
        complete("crash with " + (2 / 0))
      } ~
      path("crash") {
        throw new RuntimeException
      }
    }
  }
}

object RejectionsExceptionsExample extends App {
  new RejectionsExceptionsExample().startServer("localhost", 9000)
}
