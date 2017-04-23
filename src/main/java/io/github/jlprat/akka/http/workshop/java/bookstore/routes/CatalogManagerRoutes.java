package io.github.jlprat.akka.http.workshop.java.bookstore.routes;

import akka.actor.ActorRef;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

/**
 * Created by jlprat on 23.04.17.
 */
public class CatalogManagerRoutes extends AllDirectives {

    private ActorRef catalogActorRef;

    public CatalogManagerRoutes(ActorRef catalogActorRef) {
        this.catalogActorRef = catalogActorRef;
    }

    public Route catalogManagerRoutes() {
        return null;
    }
}
