package io.github.jlprat.akka.http.workshop.ex1

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ FlatSpec, Matchers }

/**
  * Created by @jlprat on 19/04/2017.
  */
class PathExampleSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  val routeToTest = new PathExample().route

  "PathExample" should "serve `/foo` paths without slash" in {
    Get("/foo") ~> routeToTest ~> check {
      responseAs[String] shouldBe "got foo"
      status shouldBe StatusCodes.OK
    }
  }

  it should "serve `/foo/` paths with slash" in {
    Get("/foo/") ~> routeToTest ~> check {
      responseAs[String] shouldBe "got foo"
      status shouldBe StatusCodes.OK
    }
  }

  it should "serve nested routes like `/foo/bar` only without trailing slash" in {
    Get("/foo/bar") ~> routeToTest ~> check {
      responseAs[String] shouldBe "got bar"
      status shouldBe StatusCodes.OK
    }
  }

  it should "respond with `404` if route doesn't exist" in {
    Get("/notExisting") ~> Route.seal(routeToTest) ~> check {
      status shouldBe StatusCodes.NotFound
    }

    Get("/notExisting") ~> routeToTest ~> check {
      rejections shouldBe Seq.empty // when a route doesn't handle a request it returns an empty rejection list
    }
  }

  it should "serve other top level routes like `/other`" in {
    Get("/other") ~> routeToTest ~> check {
      responseAs[String] shouldBe "OK"
      status shouldBe StatusCodes.OK
    }
  }
}
