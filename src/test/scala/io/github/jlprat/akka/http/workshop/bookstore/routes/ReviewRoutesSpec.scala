package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.model.{FormData, StatusCodes}
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestActorRef
import io.github.jlprat.akka.http.workshop.bookstore.actor.{CatalogActor, ReviewerActor}
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book, Review}
import Review._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.ReviewerActor.Reviews
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

class ReviewRoutesSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  class Fixture extends ReviewRoutes {
    override val reviewerActorRef = TestActorRef[ReviewerActor]
    override val catalogActorRef = TestActorRef[CatalogActor]

    val validCredentials = BasicHttpCredentials("Bob", "LetMeIn")
    val otherValidCredentials = BasicHttpCredentials("Alice", "LetMeIn")

    val invalidCredentials = BasicHttpCredentials("Alice", "BadPass")
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))

    val review = Review(author = Author("Charlie"), comment = "Awesome!", stars = `*****`)

    protected def setReviewBook(book: Book, review: Review): Unit = {
      addBookInCatalog(book)
      reviewerActorRef.underlyingActor.context.become(reviewerActorRef.underlyingActor.handleReviews(Map(book.isbn -> Seq(review))))
    }

    protected def addBookInCatalog(book: Book): Unit = {
      catalogActorRef.underlyingActor.catalog = catalogActorRef.underlyingActor.catalog + (book.isbn -> book)
    }

    override implicit val timeout: Timeout = 300.millis
  }

  "ReviewRoutes" should "let add reviews by producing a PUT to /review/book/{isbn}" in new Fixture {
    addBookInCatalog(book)
    Put(s"/review/book/${book.isbn}", FormData("comment" -> "It was awesome!", "stars" -> "5")) ~>
      addCredentials(validCredentials) ~>
      reviewRoutes ~>
      check {
        status shouldBe StatusCodes.OK
        entityAs[String] shouldBe "OK"
      }
  }

  it should "list reviews from a book performing a GET to /review/book/{isbn}" in new Fixture {
    setReviewBook(book, review)
    Get(s"/review/book/${book.isbn}/") ~> reviewRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Reviews] shouldBe Reviews(Seq(review))
    }
  }

  it should "reviews' author should be the authenticated user" in new Fixture {
    addBookInCatalog(book)
    Put(s"/review/book/${book.isbn}/", FormData("comment" -> "It was awesome!", "stars" -> "5")) ~>
      addCredentials(validCredentials) ~>
      reviewRoutes ~>
      check {
        status shouldBe StatusCodes.OK
        entityAs[String] shouldBe "OK"
      }

    Get(s"/review/book/${book.isbn}/") ~> reviewRoutes ~> check {
      status shouldBe StatusCodes.OK
      val reviews = responseAs[Reviews]
      reviews.reviews.size shouldBe 1
      reviews.reviews.head.author shouldBe Author("Bob")
      reviews.reviews.head.comment shouldBe "It was awesome!"
      reviews.reviews.head.stars shouldBe `*****`
    }
  }

  it should "not let write anonymous reviews" in new Fixture {
    addBookInCatalog(book)
    Put(s"/review/book/${book.isbn}", FormData("comment" -> "It was awesome!", "stars" -> "5")) ~>
      Route.seal(reviewRoutes) ~>
      check {
        status shouldBe StatusCodes.Unauthorized
      }
  }

}
