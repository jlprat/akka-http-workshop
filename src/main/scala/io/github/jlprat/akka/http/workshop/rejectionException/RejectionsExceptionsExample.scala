package io.github.jlprat.akka.http.workshop.rejectionException

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server._

/**
  * This class showcases rejections and exceptions handling
  * Created by @jlprat on 20/04/2017.
  */
class RejectionsExceptionsExample extends HttpApp {

  private val rejectionHandler = RejectionHandler.newBuilder()
    .handle {
      case _: MethodRejection =>
        complete(HttpResponse(status = StatusCodes.MethodNotAllowed, entity = "Have you tried with more conventional methods?"))
    }
    .handleNotFound(complete(HttpResponse(status = StatusCodes.NotFound, entity = "Nothing to see here!")))
    .result()

  private val exceptionHandler = ExceptionHandler.apply {
    case _: ArithmeticException => complete(HttpResponse(status = StatusCodes.InternalServerError, entity = "Do you math?"))
  }

  override protected[rejectionException] def routes: Route = handleRejections(rejectionHandler) {
    concat(
      get {
        path("onlyGet") {
          complete("got it")
        }
      },
      handleExceptions(exceptionHandler) {
        concat(
          path("division" / IntNumber / IntNumber) { (x, y) =>
            complete("Division result " + (x / y))
          },
          path("crash") {
            throw new RuntimeException
          })
      })
  }
}

object RejectionsExceptionsExample extends App {
  new RejectionsExceptionsExample().startServer("localhost", 9000)
}
