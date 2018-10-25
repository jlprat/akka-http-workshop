package io.github.jlprat.akka.http.workshop.client

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.duration._
/**
  * Created by @jlprat on 22/10/2018.
  */
class HttpClientExampleSpec extends TestKit(ActorSystem("ClientSystem")) with FlatSpecLike
  with Matchers with ScalaFutures with BeforeAndAfterAll {

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = 2.seconds)

  "HttpClientExample" should "return a a future containing the content of a page" in {

    HttpClientExample.getPage("https://github.com/status").futureValue should startWith("GitHub lives!")
  }

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)
}
