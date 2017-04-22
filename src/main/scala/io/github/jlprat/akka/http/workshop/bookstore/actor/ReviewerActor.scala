package io.github.jlprat.akka.http.workshop.bookstore.actor

import akka.actor.{Actor, ActorLogging, Props}
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor.{AddReview, ListReviews, Reviews, Success}
import io.github.jlprat.akka.http.workshop.bookstore.model.{Book, Review}

object ReviewerActor {

  sealed trait Actions

  //incoming messages
  case class AddReview(review: Review, book: Book) extends Actions
  case class ListReviews(book: Book) extends Actions

  //outgoing messages
  case class Reviews(reviews: Seq[Review]) extends Actions
  case object Success extends Actions

  def props: Props = Props(new ReviewerActor)
}

/**
  * Actor that holds all reviews for the books
  */
class ReviewerActor extends Actor with ActorLogging {
  override def receive: Receive = handleReviews(Map.empty)

  def handleReviews(reviews: Map[String, Seq[Review]]): Receive = {
    case AddReview(review, book) =>
      val currentReviews = review +: reviews.getOrElse(book.isbn, Seq.empty)
      sender() ! Success
      context.become(handleReviews(reviews + (book.isbn -> currentReviews)))
    case ListReviews(book) =>
      sender() ! Reviews(reviews.getOrElse(book.isbn, Seq.empty))
  }
}
