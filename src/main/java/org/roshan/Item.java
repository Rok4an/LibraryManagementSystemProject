package org.roshan;

import lombok.Setter;

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

    public enum ItemStatus {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
