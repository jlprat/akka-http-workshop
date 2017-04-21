package io.github.jlprat.akka.http.workshop.ex3;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCode;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Test;

/**
 * Created by jlprat on 20/04/2017.
 */
public class RejectionsExceptionsExampleJavaTest extends JUnitRouteTest {

    private RejectionsExceptionsExampleJava rejectionsExceptionsExampleJava = new RejectionsExceptionsExampleJava();

    @Test
    public void testPersonalized404() {
        testRoute(rejectionsExceptionsExampleJava.route()).run(HttpRequest.GET("/not/existing"))
                .assertStatusCode(StatusCodes.NOT_FOUND)
                .assertEntity("Nothing to see here!");
    }

    @Test
    public void testPersonalizedWrongMethod() {
        testRoute(rejectionsExceptionsExampleJava.route()).run(HttpRequest.PUT("/onlyget"))
                .assertStatusCode(StatusCodes.METHOD_NOT_ALLOWED)
                .assertEntity("Have you tried with more conventional methods?");
    }

    @Test
    public void testPersonalizedArithmeticException() {
        testRoute(rejectionsExceptionsExampleJava.route()).run(HttpRequest.GET("/zerodivision"))
                .assertStatusCode(StatusCodes.INTERNAL_SERVER_ERROR)
                .assertEntity("Do you math?");
    }

    @Test
    public void testDefaultForAnyOtherException() {
        testRoute(rejectionsExceptionsExampleJava.route()).run(HttpRequest.GET("/crash"))
                .assertStatusCode(StatusCodes.INTERNAL_SERVER_ERROR)
                .assertEntity("There was an internal server error.");
    }
}
