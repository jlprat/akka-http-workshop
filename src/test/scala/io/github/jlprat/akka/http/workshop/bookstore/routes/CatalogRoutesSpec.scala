package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestActorRef
import akka.util.Timeout
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor.Catalog
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

class CatalogRoutesSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  class Fixture extends CatalogRoutes {
    override implicit val timeout: Timeout = 300.millis

    override val catalogActorRef = TestActorRef[CatalogActor]

    protected def addBookInCatalog(book: Book): Unit = {
      catalogActorRef.underlyingActor.catalog = catalogActorRef.underlyingActor.catalog + (book.isbn -> book)    }
  }

  "CatalogRoutes" should "listen to /catalog and list all books in catalog" in new Fixture {
    Get("/catalog") ~> catalogRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Catalog] shouldBe Catalog(Set.empty)
    }

    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    addBookInCatalog(book)

    Get("/catalog") ~> catalogRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Catalog] shouldBe Catalog(Set(book))
    }

  }

  it should "listen only to GET for /catalog" in new Fixture {
    Post("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Put("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Delete("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Patch("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Head("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Options("/catalog")~> catalogRoutes ~> check {
      handled shouldBe false
    }
  }

  it should "provide a book information when asked under /catalog/book/{isbn}" in new Fixture {
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    Get(s"/catalog/book/${book.isbn}") ~> catalogRoutes ~> check {
      status shouldBe StatusCodes.NotFound
    }

    addBookInCatalog(book)

    Get(s"/catalog/book/${book.isbn}") ~> catalogRoutes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Book] shouldBe book
    }
  }

  it should "listen only to GET for /catalog/book/{isbn}" in new Fixture {
    Post("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Put("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Delete("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Patch("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Head("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
    Options("/catalog/book/123")~> catalogRoutes ~> check {
      handled shouldBe false
    }
  }
}
