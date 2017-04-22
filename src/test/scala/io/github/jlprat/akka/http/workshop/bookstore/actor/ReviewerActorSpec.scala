package io.github.jlprat.akka.http.workshop.bookstore.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book, Review}
import Review._
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class ReviewerActorSpec extends TestKit(ActorSystem("ReviewerActorSpec"))
  with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "ReviewerActor" should "accept new reviews" in {
    val reviewerActor = system.actorOf(ReviewerActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val review = Review(author = Author("John Doe"), comment = "I liked it", stars = `*****`)
    reviewerActor ! AddReview(review, book)
    expectMsg(Success)
  }

  it should "return the reviews for a given book" in {
    val reviewerActor = system.actorOf(ReviewerActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val review = Review(author = Author("John Doe"), comment = "I liked it", stars = `*****`)
    reviewerActor ! AddReview(review, book)
    expectMsg(Success)
    reviewerActor ! ListReviews(book)
    expectMsg(Reviews(Seq(review)))
  }

  it should "return empty reviews if the book has none" in {
    val reviewerActor = system.actorOf(ReviewerActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    reviewerActor ! ListReviews(book)
    expectMsg(Reviews(Seq.empty))
  }

  it should "accept and return more than one review per book" in {
    val reviewerActor = system.actorOf(ReviewerActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val review1 = Review(author = Author("John Doe"), comment = "I liked it", stars = `*****`)
    val review2 = Review(author = Author("Alice"), comment = "I liked it not", stars = `*`)
    reviewerActor ! AddReview(review1, book)
    expectMsg(Success)
    reviewerActor ! AddReview(review2, book)
    expectMsg(Success)
    reviewerActor ! ListReviews(book)
    expectMsg(Reviews(Seq(review2, review1)))
  }
}