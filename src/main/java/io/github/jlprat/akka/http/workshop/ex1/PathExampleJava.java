package io.github.jlprat.akka.http.workshop.ex1;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;

import java.util.concurrent.ExecutionException;

/**
 * This class showcases some useful Path directives
 * Created by @jlprat on 19/04/2017.
 */
public class PathExampleJava extends HttpApp {

  @Override
  protected Route routes() {
    return null;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    new PathExampleJava().startServer("localhost", 9000);
  }
}
