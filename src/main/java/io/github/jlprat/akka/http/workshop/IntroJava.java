package io.github.jlprat.akka.http.workshop;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;

import java.util.concurrent.ExecutionException;

/**
 * Short server using {@link HttpApp} to help getting started
 * Created by @jlprat on 15/04/2017.
 */
public class IntroJava extends HttpApp {

  @Override
  protected Route route() {
    return path("hello", () ->
      complete("world!")
    );
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    new IntroJava().startServer("localhost", 9000);
  }
}
