package io.github.jlprat.akka.http.workshop.ex2;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;

import java.util.concurrent.ExecutionException;

/**
 * This class showcases some useful parameter directives and matchers
 * Created by @jlprat on 20/04/2017.
 */
public class ParameterExampleJava extends HttpApp {

    @Override
    protected Route routes() {
        return null;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ParameterExampleJava().startServer("localhost", 9000);
    }
}
