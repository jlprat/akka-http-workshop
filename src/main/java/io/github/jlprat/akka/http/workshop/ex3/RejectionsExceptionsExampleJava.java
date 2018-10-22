package io.github.jlprat.akka.http.workshop.ex3;

import akka.http.javadsl.server.*;

import java.util.concurrent.ExecutionException;

/**
 * This class showcases rejections and exceptions handling
 * Created by @jlprat on 20/04/2017.
 */
public class RejectionsExceptionsExampleJava extends HttpApp {
    @Override
    protected Route routes() {
        return null;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new RejectionsExceptionsExampleJava().startServer("localhost", 9000);
    }
}
