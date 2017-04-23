package io.github.jlprat.akka.http.workshop.java.bookstore.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import io.github.jlprat.akka.http.workshop.java.bookstore.model.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlprat on 23.04.17.
 */
public class CatalogActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Map<String, Book> catalog = new HashMap<>();

    static public class AddBook {
        private final Book book;

        public AddBook(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
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
    }

    static public class QueryBook {
        private final String isbn;

        public QueryBook(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn() {
            return isbn;
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
    }

    static public class Error {
        private final String msg;

        public Error(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    static public class Success {
    }

    static public class Catalog {
        private final Collection<Book> books;

        public Catalog(Collection<Book> books) {
            this.books = books;
        }

        public Collection<Book> getBooks() {
            return books;
        }
    }

    public static Props props() {
        return Props.create(CatalogActor.class);
    }

    public CatalogActor() {
        receive(ReceiveBuilder
                .match(AddBook.class, addBook -> {
                    catalog.put(addBook.getBook().getIsbn(), addBook.getBook());
                    sender().tell(new Success(), self());
                })
                .match(RemoveBook.class, removeBook -> catalog.containsKey(removeBook.getBook().getIsbn()), removeBook -> {
                    catalog.remove(removeBook.getBook().getIsbn());
                    sender().tell(new Success(), self());
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
                .build()
        );
    }
}
