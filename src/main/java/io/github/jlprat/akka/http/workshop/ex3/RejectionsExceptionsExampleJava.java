package io.github.jlprat.akka.http.workshop.ex3;

import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.*;

import java.util.concurrent.ExecutionException;

/**
 * This class showcases rejections and exceptions handling
 * Created by @jlprat on 20/04/2017.
 */
public class RejectionsExceptionsExampleJava extends HttpApp {
    @Override
    protected Route route() {
        final RejectionHandler rejectionHandler = RejectionHandler.newBuilder()
                .handle(MethodRejection.class, rej ->
                        complete(StatusCodes.METHOD_NOT_ALLOWED, "Have you tried with more conventional methods?")
                )
                .handleNotFound(complete(StatusCodes.NOT_FOUND, "Nothing to see here!"))
                .build();
        final ExceptionHandler exceptionHandler = ExceptionHandler.newBuilder()
                .match(ArithmeticException.class, ex ->
                        complete(StatusCodes.INTERNAL_SERVER_ERROR, "Do you math?")
                ).build();
        return handleRejections(rejectionHandler, () ->
                route(
                        get(() ->
                                path("onlyget", () ->
                                        complete("got it")
                                )
                        ),
                        handleExceptions(exceptionHandler, () ->
                                route(
                                        path("zerodivision", () ->
                                                complete(Integer.toString(2 / 0))
                                        ),
                                        path("crash", () -> {
                                                    throw new RuntimeException();
                                                }
                                        )
                                )
                        )
                )
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new RejectionsExceptionsExampleJava().startServer("localhost", 9000);
    }
}
