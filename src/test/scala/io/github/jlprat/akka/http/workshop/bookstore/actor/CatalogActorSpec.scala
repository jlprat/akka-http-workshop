package io.github.jlprat.akka.http.workshop.bookstore.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor._
import io.github.jlprat.akka.http.workshop.bookstore.model.{Author, Book}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class CatalogActorSpec extends TestKit(ActorSystem("CatalogActorSpec"))
  with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "CatalogActor" should "accept new books" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    catalogActor ! AddBook(book)
    expectMsg(Success)
  }

  it should "remove books" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    catalogActor ! AddBook(book)
    expectMsg(Success)
    catalogActor ! RemoveBook(book)
    expectMsg(Success)
  }

  it should "fail removing books when the book does not exist" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    catalogActor ! RemoveBook(book)
    expectMsg(Error(s"I don't know such book - $book"))
  }

  it should "respond to book queries" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    catalogActor ! AddBook(book)
    expectMsg(Success)
    catalogActor ! QueryBook(book.isbn)
    expectMsg(BookInfo(book))
  }

  it should "respond with error to book queries when book is unknown" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    catalogActor ! QueryBook("non existing")
    expectMsg(Error(s"I don't know such isbn - non existing"))
  }

  it should "list all the catalog" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book1 = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val book2 = Book("7654321", "Doe the art", 123, Author("Jane Doe"))
    catalogActor ! AddBook(book1)
    expectMsg(Success)
    catalogActor ! AddBook(book2)
    expectMsg(Success)
    catalogActor ! ListCatalog
    expectMsg(Catalog(Set(book1, book2)))
  }

  it should "update the catalog when adding books" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book1 = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val book2 = Book("7654321", "Doe the art", 123, Author("Jane Doe"))
    catalogActor ! AddBook(book1)
    expectMsg(Success)
    catalogActor ! ListCatalog
    expectMsg(Catalog(Set(book1)))
    catalogActor ! AddBook(book2)
    expectMsg(Success)
    catalogActor ! ListCatalog
    expectMsg(Catalog(Set(book1, book2)))
  }

  it should "update the catalog when removing books" in {
    val catalogActor = system.actorOf(CatalogActor.props)
    val book1 = Book("1234567", "The art of Doe", 321, Author("Jane Doe"))
    val book2 = Book("7654321", "Doe the art", 123, Author("Jane Doe"))
    catalogActor ! AddBook(book1)
    expectMsg(Success)
    catalogActor ! AddBook(book2)
    expectMsg(Success)
    catalogActor ! ListCatalog
    expectMsg(Catalog(Set(book1, book2)))
    catalogActor ! RemoveBook(book1)
    expectMsg(Success)
    catalogActor ! ListCatalog
    expectMsg(Catalog(Set(book2)))
  }
}