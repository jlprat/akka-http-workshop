package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.pattern.ask
import akka.actor.ActorRef
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.{BookInfo, QueryBook}
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor.{AddReview, ListReviews, Reviews}
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Review}
import io.github.jlprat.akka.http.workshop.bookstore.model.Review.Stars

import scala.util.{Failure, Success}

/**
  * This trait holds all routes in charge of reviews
  */
trait ReviewRoutes extends Directives with JsonProtocol with Authentication {

  val reviewerActorRef: ActorRef
  val catalogActorRef: ActorRef

  implicit val timeout: Timeout

  val reviewRoutes: Route = pathPrefix("review" / "book" / Segment) { isbn =>
    get {
      val eventuallyBook = catalogActorRef ? QueryBook(isbn)
      onComplete(eventuallyBook) {
        case Success(BookInfo(book)) =>
          val eventuallyReview = (reviewerActorRef ? ListReviews(book)).mapTo[Reviews]
          onComplete(eventuallyReview) {
            case Success(reviews) => complete(reviews)
            case Failure(ex) => failWith(ex)
          }
        case Failure(ex) => failWith(ex)
      }
    } ~
      put {
        authenticateBasic("book-realm", myUserPassAuthenticator) { userName =>
          formFields('comment, 'stars) { (comment, stars) =>
            val review = Review(author = Author(userName), comment = comment, stars = Stars.fromString(stars))
            val eventuallyBook = catalogActorRef ? QueryBook(isbn)
            onComplete(eventuallyBook) {
              case Success(BookInfo(book)) =>
                val eventuallyReview = reviewerActorRef ? AddReview(review, book)
                onComplete(eventuallyReview) {
                  case Success(_) => complete("OK")
                  case Failure(ex) => failWith(ex)
                }
              case Failure(ex) => failWith(ex)
            }
          }
        }
      }

  }

}
