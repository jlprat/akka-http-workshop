package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.Catalog
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book, Review}
import Review._
import spray.json._

trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val authorFormat = jsonFormat1(Author)
  implicit val bookFormat = jsonFormat4(Book)
  implicit val oneStartFormat = jsonFormat1(Stars)
  implicit val reviewFormat = jsonFormat4(Review.apply)
  implicit val catalogFormat = jsonFormat1(Catalog)
}
