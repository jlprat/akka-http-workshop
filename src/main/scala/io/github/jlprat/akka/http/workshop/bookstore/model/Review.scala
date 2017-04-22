package io.github.jlprat.akka.http.workshop.bookstore.model

import java.util.Date

import io.github.jlprat.akka.http.workshop.bookstore.model.Review.Stars

object Review {

  /**
    * Models the star rating system
    */
  case class Stars protected[bookstore](stars: String)
  val `*` = Stars("*")
  val `**` = Stars("**")
  val `***` = Stars("***")
  val `****` = Stars("****")
  val `*****` = Stars("*****")

}

case class Review(author: Author, date: Long = new Date().getTime, comment: String, stars: Stars)
