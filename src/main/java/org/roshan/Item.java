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

    public Item(String title, ItemStatus status) {
        this.title = title;
        this.status = status;
    }

    /**
     * Borrows this item if it is available.
     * @throws ItemNotAvailableException if the item is not available
     */
    public void borrowItem() throws ItemNotAvailableException {
        if (this.status != ItemStatus.AVAILABLE) {
            throw new ItemNotAvailableException(
                    "Item '" + title + "' is not available. Status: " + status);
        }
        this.status = ItemStatus.BORROWED;
    }

    /**
     * Returns this item if it is currently borrowed.
     * @throws ItemNotBorrowedException if the item is not currently borrowed
     */
    public void returnItem() throws ItemNotBorrowedException {
        if (this.status != ItemStatus.BORROWED) {
            throw new ItemNotBorrowedException(
                    "Item '" + title + "' is not currently borrowed.");
        }
        this.status = ItemStatus.AVAILABLE;
    }

    /**
     * Marks this item as lost.
     */
    public void markLost() {
        this.status = ItemStatus.LOST;
    }

    public abstract int getBorrowLimit();

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof Item o)) return 0;

        if (this instanceof Book b1 && o instanceof Book b2) {
            return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
        }

        if (this instanceof DVD d1 && o instanceof DVD d2) {
            return Integer.compare(d1.getDuration(), d2.getDuration());
        }

        if (this instanceof Magazine m1 && o instanceof Magazine m2) {
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
