package io.github.jlprat.akka.http.workshop.actors

/**
  * Created by @jlprat on 24/10/2018.
  */
object Model {

  case class Book(isbn: String, title: String, pages: Int, author: Author)

  case class Author(name: String)
}
