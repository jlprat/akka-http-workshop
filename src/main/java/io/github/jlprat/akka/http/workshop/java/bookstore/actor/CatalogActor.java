package io.github.jlprat.akka.http.workshop.java.bookstore.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlprat on 23.04.17.
 */
public class CatalogActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Map<String, Book> catalog = new HashMap<>();

    static public class AddBook {
        private final Book book;

        public AddBook(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AddBook addBook = (AddBook) o;

            return book != null ? book.equals(addBook.book) : addBook.book == null;
        }

        @Override
        public int hashCode() {
            return book != null ? book.hashCode() : 0;
        }
    }

    static public class RemoveBook {
        private final Book book;

        public RemoveBook(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RemoveBook that = (RemoveBook) o;

            return book != null ? book.equals(that.book) : that.book == null;
        }

        @Override
        public int hashCode() {
            return book != null ? book.hashCode() : 0;
        }
    }

    static public class QueryBook {
        private final String isbn;

        public QueryBook(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn() {
            return isbn;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QueryBook queryBook = (QueryBook) o;

            return isbn != null ? isbn.equals(queryBook.isbn) : queryBook.isbn == null;
        }

        @Override
        public int hashCode() {
            return isbn != null ? isbn.hashCode() : 0;
        }
    }

    static public class ListCatalog {
    }

    static public class BookInfo {
        private final Book book;

        public BookInfo(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BookInfo bookInfo = (BookInfo) o;

            return book != null ? book.equals(bookInfo.book) : bookInfo.book == null;
        }

        @Override
        public int hashCode() {
            return book != null ? book.hashCode() : 0;
        }
    }

    static public class Error {
        private final String msg;

        public Error(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Error error = (Error) o;

            return msg != null ? msg.equals(error.msg) : error.msg == null;
        }

        @Override
        public int hashCode() {
            return msg != null ? msg.hashCode() : 0;
        }
    }

    static public class Success {

        private static Success success = null;

        public static Success getInstance() {
            if (success == null) success = new Success();
            return success;
        }
    }

    static public class Catalog {
        private final Collection<Book> books;

        public Catalog(Collection<Book> books) {
            this.books = books;
        }

        public Collection<Book> getBooks() {
            return books;
        }

        @Override
        public String toString() {
            return "Catalog{" +
                    "books=" + books +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Catalog catalog = (Catalog) o;

            return books != null ? books.equals(catalog.books) : catalog.books == null;
        }

        @Override
        public int hashCode() {
            return books != null ? books.hashCode() : 0;
        }
    }

    public static Props props() {
        return Props.create(CatalogActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AddBook.class, addBook -> {
                    catalog.put(addBook.getBook().getIsbn(), addBook.getBook());
                    sender().tell(Success.getInstance(), self());
                })
                .match(RemoveBook.class, removeBook -> catalog.containsKey(removeBook.getBook().getIsbn()), removeBook -> {
                    catalog.remove(removeBook.getBook().getIsbn());
                    sender().tell(Success.getInstance(), self());
                })
                .match(RemoveBook.class, removeBook -> {
                    final String msg = "I don't know such book - " + removeBook.getBook().toString();
                    log.error(msg);
                    sender().tell(new Error(msg), self());
                })
                .match(QueryBook.class, query -> catalog.containsKey(query.getIsbn()), query ->
                        sender().tell(new BookInfo(catalog.get(query.getIsbn())), self())
                )
                .match(QueryBook.class, query -> {
                    final String msg = "I don't know such isbn - " + query.getIsbn();
                    log.error(msg);
                    sender().tell(new Error(msg), self());
                })
                .match(ListCatalog.class, ignore ->
                        sender().tell(new Catalog(catalog.values()), self())
                )
                .build();
    }
}
