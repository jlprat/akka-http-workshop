package io.github.jlprat.akka.http.workshop.java.bookstore.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AddReview addReview = (AddReview) o;

            if (review != null ? !review.equals(addReview.review) : addReview.review != null) return false;
            return book != null ? book.equals(addReview.book) : addReview.book == null;
        }

        @Override
        public int hashCode() {
            int result = review != null ? review.hashCode() : 0;
            result = 31 * result + (book != null ? book.hashCode() : 0);
            return result;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListReviews that = (ListReviews) o;

            return book != null ? book.equals(that.book) : that.book == null;
        }

        @Override
        public int hashCode() {
            return book != null ? book.hashCode() : 0;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Reviews reviews1 = (Reviews) o;

            return reviews != null ? reviews.equals(reviews1.reviews) : reviews1.reviews == null;
        }

        @Override
        public int hashCode() {
            return reviews != null ? reviews.hashCode() : 0;
        }
    }

    public static class Success {
        private static Success success = null;

        public static Success getInstance() {
            if (success == null) success = new Success();
            return success;
        }
    }

    public static Props props() {
        return Props.create(ReviewerActor.class);
    }

    private Map<String, List<Review>> reviews = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AddReview.class, command -> {
                    final List<Review> bookReviews = reviews.getOrDefault(command.getBook().getIsbn(), new ArrayList<>());
                    bookReviews.add(command.getReview());
                    reviews.put(command.getBook().getIsbn(), bookReviews);
                    sender().tell(new Success(), self());
                })
                .match(ListReviews.class, command -> {
                    sender().tell(reviews.getOrDefault(command.getBook().getIsbn(), new ArrayList<>()), self());
                })
                .build();

    }
}
