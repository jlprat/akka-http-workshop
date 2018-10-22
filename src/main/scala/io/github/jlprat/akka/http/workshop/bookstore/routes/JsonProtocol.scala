package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

/**
  * Provides support for JSON serialization
  */
trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

}
