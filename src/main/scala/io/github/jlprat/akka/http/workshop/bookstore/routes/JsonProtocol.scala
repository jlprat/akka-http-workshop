package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.Catalog
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor.Reviews
import io.github.jlprat.akka.http.workshop.bookstore.model.Review._
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book, Review}
import spray.json._

/**
  * Provides support for JSON serialization
  */
trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

}
