package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.AuthenticationFailedRejection.CredentialsRejected
import akka.http.scaladsl.server.{AuthorizationFailedRejection, Route}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestActorRef
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

class CatalogManagerRoutesSpec extends FlatSpec with ScalatestRouteTest with Matchers {


  class Fixture extends CatalogManagerRoutes {
    override implicit val timeout: Timeout = 300.millis

    override val catalogActorRef = TestActorRef[CatalogActor]

    val validCredentialsAdmin = BasicHttpCredentials("Admin", "LetMeIn")
    val validCredentialsNonAdmin = BasicHttpCredentials("Alice", "LetMeIn")
    val invalidCredentialsNonAdmin = BasicHttpCredentials("Alice", "BadPass")

    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))

    protected def addBookInCatalog(book: Book): Unit = {
      catalogActorRef.underlyingActor.catalog = catalogActorRef.underlyingActor.catalog + (book.isbn -> book)
    }
  }

  "CatalogManagerRoutes" should "let add books under /catalog/admin/book" in new Fixture {
    Put("/catalog/admin/book", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }

    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book)
  }

  it should "let add books under /catalog/admin/book/" in new Fixture {
    Put("/catalog/admin/book/", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }

    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book)
  }

  it should "behave in an idempotent way when adding the same book twice" in new Fixture {
    Put("/catalog/admin/book", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book)

    Put("/catalog/admin/book", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book)
  }

  it should "let add more than one book" in new Fixture {
    Put("/catalog/admin/book/", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book)

    val otherBook = Book("1234567", "Doe in the art", 321, Author("Janette Doe"))
    Put("/catalog/admin/book/", otherBook) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map(book.isbn -> book, otherBook.isbn -> otherBook)
  }

  it should "let remove a book" in new Fixture {
    addBookInCatalog(book)
    Delete("/catalog/admin/book", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map.empty
  }

  it should "let remove a book even if it doesn't exist" in new Fixture {
    Delete("/catalog/admin/book", book) ~> addCredentials(validCredentialsAdmin) ~> catalogManagerRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
    catalogActorRef.underlyingActor.catalog shouldBe Map.empty
  }

  it should "not let add nor remove if the user is not admin" in new Fixture {
    Put("/catalog/admin/book/", book) ~> addCredentials(validCredentialsNonAdmin) ~> catalogManagerRoutes ~> check {
      rejection shouldBe AuthorizationFailedRejection
    }

    Delete("/catalog/admin/book/", book) ~> addCredentials(validCredentialsNonAdmin) ~> catalogManagerRoutes ~> check {
      rejection shouldBe AuthorizationFailedRejection
    }
  }

  it should "ask for credentials if no credentials or bad ones are provided" in new Fixture {
    Put("/catalog/admin/book/", book) ~>
      addCredentials(invalidCredentialsNonAdmin) ~>
      Route.seal(catalogManagerRoutes) ~>
      check {
        status shouldBe StatusCodes.Unauthorized
      }

    Put("/catalog/admin/book/", book) ~>
      Route.seal(catalogManagerRoutes) ~>
      check {
        status shouldBe StatusCodes.Unauthorized
      }
  }
}
