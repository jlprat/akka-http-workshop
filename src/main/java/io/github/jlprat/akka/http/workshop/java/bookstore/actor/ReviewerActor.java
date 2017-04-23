package io.github.jlprat.akka.http.workshop.java.bookstore.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Book;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Review;

import java.util.*;

/**
 * Created by jlprat on 23.04.17.
 */
public class ReviewerActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static class AddReview {
        private final Review review;
        private final Book book;

        public AddReview(Review review, Book book) {
            this.review = review;
            this.book = book;
        }

        public Review getReview() {
            return review;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class ListReviews {
        private final Book book;

        public ListReviews(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class Reviews {
        private final Collection<Review> reviews;

        public Reviews(Collection<Review> reviews) {
            this.reviews = reviews;
        }

        public Collection<Review> getReviews() {
            return reviews;
        }
    }

    public static class Success {
    }

    public static Props props() {
        return Props.create(ReviewerActor.class);
    }

    private Map<String, List<Review>> reviews = new HashMap<>();

    public ReviewerActor() {
        receive(ReceiveBuilder
                .match(AddReview.class, command -> {
                    final List<Review> bookReviews = reviews.getOrDefault(command.getBook().getIsbn(), new ArrayList<>());
                    bookReviews.add(command.getReview());
                    reviews.put(command.getBook().getIsbn(), bookReviews);
                    sender().tell(new Success(), self());
                })
                .match(ListReviews.class, command -> {
                    sender().tell(reviews.getOrDefault(command.getBook().getIsbn(), new ArrayList<>()), self());
                })
                .build());

    }
}
