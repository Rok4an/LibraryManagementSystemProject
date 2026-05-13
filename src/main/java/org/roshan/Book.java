package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)

public class Book extends Item {
    private String isbn;
    private String author;
    private String title;
    private Genre genre;

    public Book(String title, ItemStatus status, String isbn, String author, Genre genre) throws Exception {
        super(title, status);
        if (!Validation.isValidISBN(isbn)) {
            throw new Exception("Invalid ISBN: " + isbn);
        }
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public int getBorrowLimit() {
        return 1;
    }

    public enum Genre {
        ROMANCE, THRILLER, SCI_FI, FANTASY,
        MYSTERY, BIOGRAPHY, SELF_HELP, HISTORY, FICTION
    }
}
