package io.github.jlprat.akka.http.workshop.actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import io.github.jlprat.akka.http.workshop.actors.Model.Book

/**
  * This class showcases a Akka Typed behavior
  * Created by @jlprat on 24/04/2017.
  */
object CatalogBehavior {

  sealed trait Command
  case class AddBooks(book: Book, quantity: Int, replyTo: ActorRef[CatalogReply]) extends Command

  sealed trait CatalogReply
  case object OperationPerformed extends CatalogReply
  case object OperationFailed extends CatalogReply

  val catalogBehavior: Behavior[Command] = Behaviors.receive {
    case (ctx, AddBooks(book, quantity, replyTo)) if quantity > 1000 =>
      ctx.log.error("Too many books ([{}]) for [{}]", quantity, book)
      replyTo ! OperationFailed
      Behaviors.same
    case (ctx, AddBooks(book, quantity, replyTo)) =>
      ctx.log.info("Talking to other actors here would be cool")
      ctx.log.info("[{}] new books [{}] in press", quantity, book)
      replyTo ! OperationPerformed
      Behaviors.same
  }
}
