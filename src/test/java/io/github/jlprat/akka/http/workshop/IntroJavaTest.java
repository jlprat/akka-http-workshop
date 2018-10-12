package io.github.jlprat.akka.http.workshop;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Test;

/**
 * Example of how to use Test Library with Akka HTTP
 * Created by @jlprat on 15/04/2017.
 */
public class IntroJavaTest extends JUnitRouteTest {

  private IntroJava intro = new IntroJava();

  @Test
  public void testGetHello() {
    testRoute(intro.routes()).run(HttpRequest.GET("/hello"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("world!");
  }

  @Test
  public void testPostHello() {
    testRoute(intro.routes()).run(HttpRequest.POST("/hello"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("world!");
  }

  @Test
  public void testPutHello() {
    testRoute(intro.routes()).run(HttpRequest.PUT("/hello"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("world!");
  }
}
