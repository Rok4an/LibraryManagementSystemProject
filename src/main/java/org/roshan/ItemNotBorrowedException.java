package org.roshan;

public class ItemNotBorrowedException extends Exception {
    public ItemNotBorrowedException(String message) {
        super(message);
    }
}
