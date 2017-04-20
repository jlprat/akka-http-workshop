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
    protected Route route() {
        return route(
                path("listen", () ->
                        parameter("p1", p1 ->
                                parameter("p2", p2 ->
                                        complete("p1 -> " + p1 + ", p2 -> " + p2)
                                )
                        )
                ),
                path("opt", () ->
                        parameterOptional("p1", p1 ->
                                complete("p1 -> " + p1.orElse("unknown"))
                        )
                ),
                path(PathMatchers.segment("buy").slash(PathMatchers.segment()), thing ->
                        complete("You want to buy a " + thing)
                ),
                path(PathMatchers.segment("double").slash(PathMatchers.integerSegment()), number ->
                        complete(Integer.toString(number * 2))
                )
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ParameterExampleJava().startServer("localhost", 9000);
    }
}
