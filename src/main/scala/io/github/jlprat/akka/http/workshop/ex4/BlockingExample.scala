package io.github.jlprat.akka.http.workshop.ex4

import java.io.BufferedInputStream
import java.util.Scanner

import akka.http.scaladsl.model.ContentTypes.`text/plain(UTF-8)`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.HttpEntity.Chunked
import akka.http.scaladsl.model.MediaTypes.`application/pdf`
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.stream.scaladsl.StreamConverters
import org.apache.commons.net.ftp.FTPClient

import scala.concurrent.Future

/**
  * This class showcases how to deal with blocking
  * Created by @jlprat on 21/04/2017.
  */
class BlockingExample extends HttpApp {

  private implicit lazy val blockingDispatcher = systemReference.get.dispatchers.lookup("my-blocking-dispatcher")

  override protected def route: Route = path("files") {
    complete(
      Future {
        val ftp = new FTPClient
        ftp.connect("ftp.fu-berlin.de")
        ftp.login("anonymous", "")
        ftp.changeWorkingDirectory("doc/o-reilly")
        val files = ftp.listFiles()
        ftp.disconnect()
        HttpEntity(
          `text/plain(UTF-8)`,
          files.map(_.getName).mkString("\n")
        )
      }
    )
  }
}

object BlockingExample extends App {
  new BlockingExample().startServer("localhost", 9000)
}