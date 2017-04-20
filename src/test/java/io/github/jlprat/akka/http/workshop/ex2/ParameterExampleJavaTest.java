package io.github.jlprat.akka.http.workshop.ex2;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Test;

/**
 * Created by @jlprat on 20/04/2017.
 */
public class ParameterExampleJavaTest extends JUnitRouteTest {

    private ParameterExampleJava routeHolder = new ParameterExampleJava();

    @Test
    public void testProcessQueryParameters(){
        testRoute(routeHolder.route()).run(HttpRequest.GET("/listen?p1=foo&p2=bar"))
                .assertEntity("p1 -> foo, p2 -> bar")
                .assertStatusCode(StatusCodes.OK);
    }

    @Test
    public void testIgnoreMissingQueryParameters(){
        testRoute(routeHolder.route()).run(HttpRequest.GET("/listen?p1=foo"))
                .assertStatusCode(StatusCodes.NOT_FOUND);
    }

    @Test
    public void testProcessOptionalQueryParameters(){
        testRoute(routeHolder.route()).run(HttpRequest.GET("/opt?p1=foo"))
                .assertEntity("p1 -> foo")
                .assertStatusCode(StatusCodes.OK);

        testRoute(routeHolder.route()).run(HttpRequest.GET("/opt"))
                .assertEntity("p1 -> unknown")
                .assertStatusCode(StatusCodes.OK);
    }

    @Test
    public void testProcessPahtParametersAsString(){
        testRoute(routeHolder.route()).run(HttpRequest.GET("/buy/house"))
                .assertEntity("You want to buy a house")
                .assertStatusCode(StatusCodes.OK);
    }

    @Test
    public void testProcessPahtParametersAsInt(){
        testRoute(routeHolder.route()).run(HttpRequest.GET("/double/3"))
                .assertEntity("6") // todo check assert typed
                .assertStatusCode(StatusCodes.OK);
    }
}
