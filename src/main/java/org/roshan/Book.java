package org.roshan;

public class Book extends Item {
    private String isbn;
    private String author;
    private String title;
    private Genre genre;

    public enum Genre {
        ROMANCE, THRILLER, SCI_FI, FANTASY,
        MYSTERY, BIOGRAPHY, SELF_HELP, HISTORY, FICTION
    }
}
