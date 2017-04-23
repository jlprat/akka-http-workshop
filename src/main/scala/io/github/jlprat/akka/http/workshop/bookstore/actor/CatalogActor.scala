package io.github.jlprat.akka.http.workshop.bookstore.actor

import akka.actor.{Actor, ActorLogging, Props}
import io.github.jlprat.akka.http.workshop.bookstore.actor.CatalogActor._
import io.github.jlprat.akka.http.workshop.bookstore.model.Book

object CatalogActor {

  sealed trait Messages
  // incoming
  case class AddBook(book: Book) extends Messages
  case class RemoveBook(book: Book) extends Messages
  case class QueryBook(isbn: String) extends Messages
  case object ListCatalog extends Messages

  // outgoing
  case class BookInfo(book: Book) extends Messages
  case class Error(msg: String) extends Messages
  case object Success extends Messages
  case class Catalog(books: Set[Book]) extends Messages

  def props: Props = Props(new CatalogActor)
}

/**
  * Actor that keeps the book catalog
  */
class CatalogActor extends Actor with ActorLogging {

  var catalog: Map[String, Book] = Map.empty

  override def receive: Receive = {
    case AddBook(book) =>
      catalog = catalog + (book.isbn -> book)
      sender() ! Success
    case RemoveBook(book) if catalog.contains(book.isbn) =>
      catalog = catalog - book.isbn
      sender() ! Success
    case RemoveBook(book) =>
      val msg = s"I don't know such book - $book"
      log.error(msg)
      sender() ! Error(msg)
    case QueryBook(isbn) if catalog.contains(isbn) =>
      sender() ! BookInfo(catalog(isbn))
    case QueryBook(isbn) =>
      val msg = s"I don't know such isbn - $isbn"
      log.error(msg)
      sender() ! Error(msg)
    case ListCatalog =>
      sender() ! Catalog(catalog.values.toSet)
  }
}
