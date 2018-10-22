package io.github.jlprat.akka.http.workshop.client

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by @jlprat on 22/10/2018.
  */
class HttpClientExampleSpec extends FlatSpec with ScalatestRouteTest with Matchers with ScalaFutures  {

  "HttpClientExample" should "return a a future containing the content of a page" in {

    HttpClientExample.getPage("https://github.com/status").futureValue should startWith("GitHub lives!")
  }
}
