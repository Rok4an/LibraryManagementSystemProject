package org.roshan;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Item implements Sortable {
    protected String id;
    protected String title;
    @Setter protected ItemStatus status;
    private static int nextId = 1;

    public Item(String title, ItemStatus status) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = status;
    }

    public boolean borrowItem() throws Exception {
        if (this.status != ItemStatus.AVAILABLE) {
            throw new Exception("Item '" + title + "' is not available. Status: " + status);
        }
        this.status = ItemStatus.BORROWED;
        return true;
    }

    public boolean returnItem() throws Exception {
        if (this.status != ItemStatus.BORROWED) {
            throw new Exception("Item '" + title + "' is not currently borrowed.");
        }
        this.status = ItemStatus.AVAILABLE;
        return true;
    }

    public enum ItemStatus {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
