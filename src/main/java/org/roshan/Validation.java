package org.roshan;

public class Validation {
        /**
         * Checks if the ISBN is exactly 13 digits.
         * @param isbn the ISBN value
         * @return true if the ISBN is valid, false otherwise
         */
        public static boolean isValidISBN(String isbn) {
            return isbn != null && isbn.matches("\\d{13}");
        }

        /**
         * Checks if an ID is exactly 6 digits.
         * @param id the ID value
         * @return true if the ID is valid, false otherwise
         */
        public static boolean isValidId(String id) {
            return id != null && id.matches("\\d{6}");
        }

        /**
         * Checks if a name contains at least one letter.
         * @param name the name to validate
         * @return true if the name contains at least one letter, false otherwise
         */
        public static boolean isValidName(String name) {
            return name != null && name.matches(".*[A-Za-z]*.");
        }

        /**
         * Checks if a magazine issue number is valid (positive integer).
         * @param issue the issue number
         * @return true if the issue number is valid, false otherwise
         */
        public static boolean isValidIssueId(int issue) {
            return issue > 0;
        }

        /**
         * Checks if a system-generated unique ID is valid.
         * Format: One uppercase letter + six digits (e.g., S000001).
         * @param id the unique ID
         * @return true if the ID matches the required format, false otherwise
         */
        public static boolean isValidUniqueId(String id) {
            return id != null && id.matches("[A-Z]\\d{6}");
        }
}
