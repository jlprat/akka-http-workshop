package io.github.jlprat.akka.http.workshop

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ FlatSpec, Matchers }

/**
  * Example of how to use Test Library with Akka HTTP
  * Created by @jlprat on 15/04/2017.
  */
class IntroSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  val intro = new Intro

  "Intro" should "respond to GET requests to hello" in {
    Get("/hello") ~> intro.routes ~> check {
      responseAs[String] shouldBe "world!"
      status shouldBe StatusCodes.OK
    }
  }
  it should "accept also POST" in {
    Post("/hello") ~> intro.routes ~> check {
      responseAs[String] shouldBe "world!"
      status shouldBe StatusCodes.OK
    }
  }

  it should "accept also PUT" in {
    Put("/hello") ~> intro.routes ~> check {
      responseAs[String] shouldBe "world!"
      status shouldBe StatusCodes.OK
    }
  }
}
