package io.github.jlprat.akka.http.workshop.actors

import akka.actor.testkit.typed.scaladsl.{BehaviorTestKit, TestInbox}
import akka.actor.typed.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.jlprat.akka.http.workshop.actors.Model.{Author, Book}
import org.scalatest.{FlatSpec, Matchers}

class CatalogRouteExampleTest extends FlatSpec with ScalatestRouteTest with Matchers {


  class Fixture extends CatalogRouteExample.CatalogRoute {
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))

    val testKit = BehaviorTestKit(CatalogBehavior.catalogBehavior)

    override val catalogBehavior: ActorRef[CatalogBehavior.Command] = testKit.ref
  }

  "CatalogManagerRoutes" should "add books with given quantity if below 1000" in new Fixture {
    Put("/catalog/book/100", book) ~> catalogRoute ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "OK"
    }
  }

  it should "fail to add books with given quantity if over 1000" in new Fixture {
    Put("/catalog/book/20000", book) ~> catalogRoute ~> check {
      status shouldBe StatusCodes.BadRequest
    }
  }

}
