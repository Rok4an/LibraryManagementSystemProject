package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends Item {
    private String isbn;
    private Genre genre;
    private String author;

    public Book(String title, ItemStatus status, String isbn, Genre genre, String author)
            throws InvalidISBNException {
        super(title, status);
        if (!Validation.isValidISBN(isbn)) {
            throw new InvalidISBNException("Invalid ISBN: " + isbn);
        }
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
    }

    @Override
    public int getBorrowLimit() {
        return 1;
    }

    public enum Genre {
        ROMANCE, THRILLER, SCI_FI, FANTASY,
        MYSTERY, BIOGRAPHY, SELF_HELP, HISTORY
    }
}
