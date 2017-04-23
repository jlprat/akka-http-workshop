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
  implicit val authorFormat = jsonFormat1(Author)
  implicit val bookFormat = jsonFormat4(Book)
  implicit val oneStartFormat = jsonFormat1(Stars.apply)
  implicit val reviewFormat = jsonFormat4(Review.apply)
  implicit val reviewsFormat = jsonFormat1(Reviews)
  implicit val catalogFormat = jsonFormat1(Catalog)
}
