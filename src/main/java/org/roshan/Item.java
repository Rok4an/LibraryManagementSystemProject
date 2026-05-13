package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public abstract class Item implements Sortable {
    protected String id;
    protected String title;
    protected ItemStatus status;
    private static int nextId = 1;

    public Item(String title, ItemStatus status) {
        this.id = String.format("%06d", nextId++);
        this.title = title;
        this.status = status;
    }

    /**
     * Borrows this item if it is available.
     * @throws Exception if the item is not available
     */
    public boolean borrowItem() throws Exception {
        if (this.status != ItemStatus.AVAILABLE) {
            throw new Exception("Item " + title + " is not available. Status: " + status);
        }
        this.status = ItemStatus.BORROWED;
        return true;
    }

    /**
     * Returns this item if it is currently borrowed.
     * @throws Exception if the item is not currently borrowed
     */
    public boolean returnItem() throws Exception {
        if (this.status != ItemStatus.BORROWED) {
            throw new Exception("Item " + title + " is not currently borrowed.");
        }
        this.status = ItemStatus.AVAILABLE;
        return true;
    }

    public abstract int getBorrowLimit();

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof Item o)) return 0;

        if (this instanceof Book && o instanceof Book) {
            Book b1 = (Book) this;
            Book b2 = (Book) o;
            return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
        }

        if (this instanceof DVD && o instanceof DVD) {
            DVD d1 = (DVD) this;
            DVD d2 = (DVD) o;
            return Integer.compare(d1.getDuration(), d2.getDuration());
        }

        if (this instanceof Magazine && o instanceof Magazine) {
            Magazine m1 = (Magazine) this;
            Magazine m2 = (Magazine) o;
            return Integer.compare(m1.getIssueNumber(), m2.getIssueNumber());
        }

        return this.title.compareToIgnoreCase(o.getTitle());
    }

    public enum ItemStatus {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
