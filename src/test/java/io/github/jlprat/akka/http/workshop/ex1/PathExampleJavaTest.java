package io.github.jlprat.akka.http.workshop.ex1;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Test;

/**
 * Created by @jlprat on 19/04/2017.
 */
public class PathExampleJavaTest extends JUnitRouteTest {

  private PathExampleJava routeHolder = new PathExampleJava();

  @Test
  public void testListenToFoo() {
    testRoute(routeHolder.routes()).run(HttpRequest.GET("/foo"))
      .assertEntity("got foo")
      .assertStatusCode(StatusCodes.OK);
  }

  @Test
  public void testListenToFooTrailingSlash() {
    testRoute(routeHolder.routes()).run(HttpRequest.GET("/foo/"))
      .assertEntity("got foo")
      .assertStatusCode(StatusCodes.OK);
  }

  @Test
  public void testListenToNestedBar() {
    testRoute(routeHolder.routes()).run(HttpRequest.GET("/foo/bar"))
      .assertEntity("got bar")
      .assertStatusCode(StatusCodes.OK);
  }

  @Test
  public void testListenToOtherRoutes() {
    testRoute(routeHolder.routes()).run(HttpRequest.GET("/other"))
      .assertEntity("OK")
      .assertStatusCode(StatusCodes.OK);
  }

  @Test
  public void testListenToOtherRoutesOnlyOnGet() {
    testRoute(routeHolder.routes()).run(HttpRequest.POST("/other"))
            .assertStatusCode(StatusCodes.METHOD_NOT_ALLOWED);
  }

  @Test
  public void testRespondNotFound() {
    testRoute(routeHolder.routes()).run(HttpRequest.GET("/notExisting"))
      .assertStatusCode(StatusCodes.NOT_FOUND);

  }
}
