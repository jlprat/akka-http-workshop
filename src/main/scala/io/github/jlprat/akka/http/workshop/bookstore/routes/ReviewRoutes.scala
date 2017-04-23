package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.{BookInfo, Error, QueryBook}
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor.{AddReview, ListReviews, Reviews}
import io.github.jlprat.akka.http.workshop.bookstore.model.Review.Stars
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Review}

import scala.util.{Failure, Success}

/**
  * This trait holds all routes in charge of reviews
  */
trait ReviewRoutes extends Directives with JsonProtocol with Authentication {

  val reviewRoutes: Route = ???

  }

}
