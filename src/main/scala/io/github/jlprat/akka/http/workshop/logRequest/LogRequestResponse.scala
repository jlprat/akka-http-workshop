package io.github.jlprat.akka.http.workshop.logRequest

import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import akka.http.scaladsl.server.directives.{LogEntry, LoggingMagnet}
import akka.http.scaladsl.server.{Directive0, HttpApp, Route, RouteResult}
import spray.json.DefaultJsonProtocol


class LogRequestResponse extends HttpApp with DefaultJsonProtocol {

  private def logTimeToSendStartResponse(loggingAdapter: LoggingAdapter, requestTimestamp: Long)
                                        (req: HttpRequest)(res: RouteResult): Unit = {
    val entry = res match {
      case Complete(resp) =>
        val responseTimestamp: Long = System.nanoTime
        val elapsedTime: Long = (responseTimestamp - requestTimestamp) / 1000
        LogEntry(s"""Logged Request:${req.method}:${req.uri}:${resp.status}:${elapsedTime}µs""", Logging.InfoLevel)
      case Rejected(reason) =>
        LogEntry(s"Rejected Reason: ${reason.mkString(",")}", Logging.InfoLevel)
    }
    entry.logTo(loggingAdapter)
  }

  private def printResponseTime(log: LoggingAdapter): HttpRequest => RouteResult => Unit = {
    val requestTimestamp = System.nanoTime
    logTimeToSendStartResponse(log, requestTimestamp)(_)
  }

  val logResponseTime: Directive0 = logRequestResult(LoggingMagnet(printResponseTime))

  override protected def routes: Route = ???
}

object LogRequestResponse extends App {
  new LogRequestResponse().startServer("localhost", 9000)
}
