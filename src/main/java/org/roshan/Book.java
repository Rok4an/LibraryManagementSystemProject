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

    public enum Genre {
        ROMANCE, THRILLER, SCI_FI, FANTASY,
        MYSTERY, BIOGRAPHY, SELF_HELP, HISTORY, FICTION
    }
}
