package io.github.jlprat.akka.http.workshop.actors

import akka.actor.testkit.typed.scaladsl.{BehaviorTestKit, TestInbox}
import akka.actor.typed.Behavior
import io.github.jlprat.akka.http.workshop.actors.Model.{Author, Book}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by @jlprat on 24/10/2018.
  */
class CatalogBehaviorSpec extends FlatSpec with Matchers {

  val author = Author("Mercè Rodoreda")
  val book = Book("978-84-8437-048-2", "La plaça del Diamant", 200, author)

  "CatalogBehavior" should "reply with OperationPerformed when quantity is below 1000" in {
    val testKit = BehaviorTestKit(CatalogBehavior.catalogBehavior)

    val receiverInbox = TestInbox[CatalogBehavior.CatalogReply]("ReceiverStub")

    val addBooks = CatalogBehavior.AddBooks(book, 100, receiverInbox.ref)

    testKit.run(addBooks)

    receiverInbox.expectMessage(CatalogBehavior.OperationPerformed)

    testKit.returnedBehavior shouldBe Behavior.same
  }

  it should "reply with OperationFailed when quantity is over 1000" in {
    val testKit = BehaviorTestKit(CatalogBehavior.catalogBehavior)

    val receiverInbox = TestInbox[CatalogBehavior.CatalogReply]("ReceiverStub")

    val addBooks = CatalogBehavior.AddBooks(book, 2000, receiverInbox.ref)

    testKit.run(addBooks)

    receiverInbox.expectMessage(CatalogBehavior.OperationFailed)

    testKit.returnedBehavior shouldBe Behavior.same
  }
}
