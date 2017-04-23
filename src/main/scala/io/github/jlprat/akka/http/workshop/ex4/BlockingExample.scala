package io.github.jlprat.akka.http.workshop.ex4

import akka.http.scaladsl.model.ContentTypes.`text/plain(UTF-8)`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.{HttpApp, Route}
import org.apache.commons.net.ftp.FTPClient

import scala.concurrent.Future

/**
  * This class showcases how to deal with blocking
  * Created by @jlprat on 21/04/2017.
  */
class BlockingExample extends HttpApp {

  override protected def route: Route = ???
}

object BlockingExample extends App {
  new BlockingExample().startServer("localhost", 9000)
}