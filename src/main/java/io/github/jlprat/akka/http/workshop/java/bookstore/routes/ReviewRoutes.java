package io.github.jlprat.akka.http.workshop.java.bookstore.routes;

import akka.actor.ActorRef;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

/**
 * Created by jlprat on 23.04.17.
 */
public class ReviewRoutes extends AllDirectives {

    private ActorRef catalogActorRef;
    private ActorRef reviewgActorRef;

    public ReviewRoutes(ActorRef catalogActorRef, ActorRef reviewgActorRef) {
        this.catalogActorRef = catalogActorRef;
        this.reviewgActorRef = reviewgActorRef;
    }

    public Route reviewRoutes() {
        return null;
    }
}
