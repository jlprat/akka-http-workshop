package io.github.jlprat.akka.http.workshop.ex3

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by @jlprat on 20/04/2017.
  */
class RejectionsExceptionsExampleSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  private val routeToTest = new RejectionsExceptionsExample().route

  "RejectionExceptionsExample" should "have a personalized message for 404" in {
    Get("/not/existing") ~> routeToTest ~> check {
      status shouldBe StatusCodes.NotFound
      responseAs[String] shouldBe "Nothing to see here!"
    }
  }

  it should "have a personalized message for wrong method" in {
    Put("/getonly") ~> routeToTest ~> check {
      status shouldBe StatusCodes.MethodNotAllowed
      responseAs[String] shouldBe "Have you tried with more conventional methods?"
    }
  }

  it should "provide special message when arithmetic exceptions" in {
    Get("/zerodivision") ~> routeToTest ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Do you math?"
    }
  }

  it should "provide default message for any other exception" in {
    Get("/crash") ~> routeToTest ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "There was an internal server error."
    }
  }
}
