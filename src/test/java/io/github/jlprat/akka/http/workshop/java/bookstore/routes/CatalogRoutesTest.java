package io.github.jlprat.akka.http.workshop.java.bookstore.routes;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.scaladsl.model.StatusCodes;
import akka.testkit.TestActorRef;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Author;
import io.github.jlprat.akka.http.workshop.java.bookstore.actor.CatalogActor;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Book;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jlprat on 23.04.17.
 */
public class CatalogRoutesTest extends JUnitRouteTest {

    TestActorRef<CatalogActor> catalogActorRef = TestActorRef.create(system(), CatalogActor.props());
    TestRoute route = testRoute(new CatalogRoutes(catalogActorRef).catalogRoutes());

    @Test
    public void testListAllBooksInCatalog() {
        route.run(HttpRequest.GET("/catalog"))
                .assertStatusCode(StatusCodes.OK())
                .assertEntityAs(Jackson.unmarshaller(CatalogActor.Catalog.class), new CatalogActor.Catalog(new ArrayList<>()));

        Book book = new Book("1234567", "The art of Doe", 321, new Author("Jane Doe"));
        catalogActorRef.underlyingActor().catalog.put(book.getIsbn(), book);

        route.run(HttpRequest.GET("/catalog"))
                .assertStatusCode(StatusCodes.OK())
                .assertEntityAs(Jackson.unmarshaller(CatalogActor.Catalog.class), new CatalogActor.Catalog(Collections.singletonList(book)));
    }

    @Test
    public void testListenOnlyToGet() {
        route.run(HttpRequest.POST("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.PUT("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.DELETE("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.PATCH("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.HEAD("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.OPTIONS("/catalog"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
    }

    @Test
    public void testListenToBookInfo() {
        Book book = new Book("1234567", "The art of Doe", 321, new Author("Jane Doe"));
        route.run(HttpRequest.GET("/catalog/book/" + book.getIsbn()))
                .assertStatusCode(StatusCodes.NotFound());

        catalogActorRef.underlyingActor().catalog.put(book.getIsbn(), book);

        route.run(HttpRequest.GET("/catalog/book/" + book.getIsbn()))
                .assertStatusCode(StatusCodes.OK())
                .assertEntityAs(Jackson.unmarshaller(Book.class), book);
    }

    @Test
    public void testListenOnlyToGetBookInfo() {
        route.run(HttpRequest.POST("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.PUT("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.DELETE("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.PATCH("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.HEAD("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
        route.run(HttpRequest.OPTIONS("/catalog/book/123"))
                .assertStatusCode(StatusCodes.MethodNotAllowed());
    }
}
