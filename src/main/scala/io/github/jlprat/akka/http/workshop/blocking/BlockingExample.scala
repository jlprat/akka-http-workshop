package io.github.jlprat.akka.http.workshop.blocking

import akka.http.scaladsl.model.ContentTypes.`text/plain(UTF-8)`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.{HttpApp, Route}
import org.apache.commons.net.ftp.FTPClient

import scala.concurrent.{ExecutionContext, Future}

/**
  * This class showcases how to deal with blocking
  * Created by @jlprat on 21/04/2017.
  */
class BlockingExample extends HttpApp {

  private implicit lazy val blockingDispatcher: ExecutionContext = systemReference.get.dispatchers.lookup("my-blocking-dispatcher")

  override protected def routes: Route = concat(
    path("files") {
      complete(
        Future {
          val ftp = new FTPClient
          ftp.connect("ftp.fu-berlin.de")
          ftp.login("anonymous", "")
          ftp.enterLocalPassiveMode()
          ftp.changeWorkingDirectory("doc/o-reilly")
          val files = ftp.listFiles()
          ftp.disconnect()
          HttpEntity(
            `text/plain(UTF-8)`,
            files.map(_.getName).mkString("\n")
          )
        }
      )
    },
    path("status") {
      complete("Alive")
    })
}

object BlockingExample extends App {
  new BlockingExample().startServer("localhost", 9000)
}