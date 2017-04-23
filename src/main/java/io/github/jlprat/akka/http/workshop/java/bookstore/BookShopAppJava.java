package io.github.jlprat.akka.http.workshop.java.bookstore;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import io.github.jlprat.akka.http.workshop.java.bookstore.actor.ReviewerActor;
import io.github.jlprat.akka.http.workshop.java.bookstore.actor.CatalogActor;
import io.github.jlprat.akka.http.workshop.java.bookstore.routes.CatalogManagerRoutes;
import io.github.jlprat.akka.http.workshop.java.bookstore.routes.CatalogRoutes;
import io.github.jlprat.akka.http.workshop.java.bookstore.routes.ReviewRoutes;

import java.util.concurrent.ExecutionException;

/**
 * Bootstraps the HTTP routes
 * Created by @jlprat on 23/04/2017
 */
public class BookShopAppJava extends HttpApp {


    private final CatalogManagerRoutes catalogManagerRoutes;
    private final CatalogRoutes catalogRoutes;
    private final ReviewRoutes reviewRoutes;

    BookShopAppJava(ActorRef catalogActorRef, ActorRef reviewerActorRef) {
        catalogManagerRoutes = new CatalogManagerRoutes(catalogActorRef);
        catalogRoutes = new CatalogRoutes(catalogActorRef);
        reviewRoutes = new ReviewRoutes(catalogActorRef, reviewerActorRef);
    }

    @Override
    protected Route route() {
        return route(catalogManagerRoutes.catalogManagerRoutes(),
                catalogRoutes.catalogRoutes(),
                reviewRoutes.reviewRoutes());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ActorSystem system = ActorSystem.apply("BookShopJava");

        ActorRef catalogActorRef = system.actorOf(CatalogActor.props());
        ActorRef reviewerActorRef = system.actorOf(ReviewerActor.props());

        new BookShopAppJava(catalogActorRef, reviewerActorRef)
                .startServer("localhost", 9000);

        system.terminate();
    }
}
