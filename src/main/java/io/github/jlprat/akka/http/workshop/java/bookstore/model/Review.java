package io.github.jlprat.akka.http.workshop.java.bookstore.model;

/**
 * Created by jlprat on 23.04.17.
 */
public class Review {

    private Author author;
    private String comment;
    private long timestamp;
    private int stars;

    public Review(Author author, String comment, int stars) {
        this.author = author;
        this.comment = comment;
        this.stars = stars;
        this.timestamp = System.currentTimeMillis();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Review{" +
                "author=" + author +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", stars=" + stars +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (timestamp != review.timestamp) return false;
        if (stars != review.stars) return false;
        if (author != null ? !author.equals(review.author) : review.author != null) return false;
        return comment != null ? comment.equals(review.comment) : review.comment == null;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + stars;
        return result;
    }
}
