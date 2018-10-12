package io.github.jlprat.akka.http.workshop.ex2

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by @jlprat on 20/04/2017.
  */
class ParameterExampleSpec  extends FlatSpec with ScalatestRouteTest with Matchers {

  val routeToTest = new ParameterExample().routes

  "ParameterExample" should "process query parameters" in {
    Get("/listen?p1=foo&p2=bar") ~> routeToTest ~> check {
      responseAs[String] shouldBe "p1 -> foo, p2 -> bar"
      status shouldBe StatusCodes.OK
    }
  }

  it should "ignore if parameters not there" in {
    Get("/listen?p1=foo") ~> routeToTest ~> check {
      handled shouldBe false
    }
  }

  it should "process optional parameters" in {
    Get("/opt?p1=foo") ~> routeToTest ~> check {
      responseAs[String] shouldBe "p1 -> foo"
      status shouldBe StatusCodes.OK
    }

    Get("/opt") ~> routeToTest ~> check {
      responseAs[String] shouldBe "p1 -> unknown"
      status shouldBe StatusCodes.OK
    }
  }

  it should "process string path parameters" in {
    Get("/buy/house") ~> routeToTest ~> check {
      responseAs[String] shouldBe "You want to buy a house"
      status shouldBe StatusCodes.OK
    }
  }

  it should "process path parameters as ints" in {
    Get("/double/3") ~> routeToTest ~> check {
      responseAs[String] shouldBe "6"
      status shouldBe StatusCodes.OK
    }
  }
}