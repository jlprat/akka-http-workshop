package io.github.jlprat.akka.http.workshop.ex4;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;

import java.util.concurrent.ExecutionException;

/**
 * This class showcases how to deal with blocking
 * Created by @jlprat on 21/04/2017.
 */
public class BlockingExampleJava extends HttpApp {
    @Override
    protected Route routes() {
        return null;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new BlockingExampleJava().startServer("localhost", 9000);
    }
}
