package org.roshan;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        if (isbn == null) return false;
        String cleaned = isbn.replaceAll("-", "");
        return cleaned.matches("\\d{10}|\\d{13}");
    }
}
