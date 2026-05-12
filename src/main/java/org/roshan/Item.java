package org.roshan;

public abstract class Item implements Sortable {
    protected String id;
    protected String title;
    protected ItemStatus status;
    private static int nextId = 1;

    public enum ItemStatus {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
