package org.roshan;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        return isbn != null && isbn.matches("\\d{13}");
    }

    public static boolean isValidId(String id) {
        return id != null && id.matches("\\d{6}");
    }
}
