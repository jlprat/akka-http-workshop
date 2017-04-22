package io.github.jlprat.akka.http.workshop.bookstore.model

import java.util.Date

import io.github.jlprat.akka.http.workshop.bookstore.model.Review.Stars

object Review {

  /**
    * Models the star rating system
    */
  sealed trait Stars
  case object `*` extends Stars
  case object `**` extends Stars
  case object `***` extends Stars
  case object `****` extends Stars
  case object `*****` extends Stars

}

case class Review(author: Author, date: Date = new Date(), comment: String, stars: Stars)
