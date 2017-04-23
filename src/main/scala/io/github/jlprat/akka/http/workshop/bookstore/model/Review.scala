package io.github.jlprat.akka.http.workshop.bookstore.model

import java.util.Date

import io.github.jlprat.akka.http.workshop.bookstore.model.Review.Stars

object Review {

  /**
    * Models the star rating system
    */
  case class Stars protected[bookstore](stars: String)
  val `*` = Stars("1")
  val `**` = Stars("2")
  val `***` = Stars("3")
  val `****` = Stars("4")
  val `*****` = Stars("5")

  object Stars {
    def fromString(stars: String): Stars = stars match {
      case "1" => `*`
      case "2" => `**`
      case "3" => `***`
      case "4" => `****`
      case "5" => `*****`
    }
  }
}

case class Review(author: Author, date: Long = new Date().getTime, comment: String, stars: Stars)
