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

    protected static int nextId = 1;

    public Item(String title, ItemStatus status) {
        this.id = String.format("%06d", nextId++);
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

        return switch (this) {

            case Book b1 -> {
                if (o instanceof Book b2) {
                    yield b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
                }
                yield this.title.compareToIgnoreCase(o.getTitle());
            }

            case DVD d1 -> {
                if (o instanceof DVD d2) {
                    yield Integer.compare(d1.getDuration(), d2.getDuration());
                }
                yield this.title.compareToIgnoreCase(o.getTitle());
            }

            case Magazine m1 -> {
                if (o instanceof Magazine m2) {
                    yield Integer.compare(m1.getIssueNumber(), m2.getIssueNumber());
                }
                yield this.title.compareToIgnoreCase(o.getTitle());
            }

            default -> this.title.compareToIgnoreCase(o.getTitle());
        };
    }

    public enum ItemStatus {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
