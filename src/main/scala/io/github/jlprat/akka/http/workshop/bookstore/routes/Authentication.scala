package io.github.jlprat.akka.http.workshop.bookstore.routes

import akka.http.scaladsl.server.directives.Credentials

/**
  * Takes care of authentication matters
  */
trait Authentication {
  //dummy auth
  def myUserPassAuthenticator(credentials: Credentials): Option[String] = {
    credentials match {
      case cred@Credentials.Provided(id) if cred.verify("LetMeIn") => Some(id)
      case _ => None
    }
  }
}
