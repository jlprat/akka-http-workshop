package io.github.jlprat.akka.http.workshop.actors

object Model {

  case class Book(isbn: String, title: String, pages: Int, author: Author)

  case class Author(name: String)
}
