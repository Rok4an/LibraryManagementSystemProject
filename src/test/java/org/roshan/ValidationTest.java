package org.roshan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationTest {

    @Test
    @DisplayName("Validation isValidISBN(): '9780547928227' (The Hobbit) -> true")
    void TestIsValidISBN1() {
        String isbn = "9780547928227";

        Assertions.assertTrue(Validation.isValidISBN(isbn));
    }

    @Test
    @DisplayName("Validation isValidISBN(): '978054' (too short) -> false")
    void TestIsValidISBN2() {
        String isbn = "978054";

        Assertions.assertFalse(Validation.isValidISBN(isbn));
    }

    @Test
    @DisplayName("Validation isValidId(): '000001' (The Hobbit ID) -> true")
    void TestIsValidId1() {
        String id = "000001";

        Assertions.assertTrue(Validation.isValidId(id));
    }

    @Test
    @DisplayName("Validation isValidId(): '00001' (5 digits only) -> false")
    void TestIsValidId2() {
        String id = "00001";

        Assertions.assertFalse(Validation.isValidId(id));
    }

    @Test
    @DisplayName("Validation isValidUniqueId(): 'S000006' (Alice) -> true")
    void TestIsValidUniqueId1() {
        String id = "S000006";

        Assertions.assertTrue(Validation.isValidUniqueId(id));
    }

    @Test
    @DisplayName("Validation isValidUniqueId(): 'T000007' (Mr.Brown) -> true")
    void TestIsValidUniqueId2() {
        String id = "T000007";

        Assertions.assertTrue(Validation.isValidUniqueId(id));
    }

    @Test
    @DisplayName("Validation isValidIssueId(): 202 (National Geographic issue) -> true")
    void TestIsValidIssueId1() {
        int issue = 202;

        Assertions.assertTrue(Validation.isValidIssueId(issue));
    }

    @Test
    @DisplayName("Validation isValidIssueId(): -1 -> false")
    void TestIsValidIssueId2() {
        int issue = -1;

        Assertions.assertFalse(Validation.isValidIssueId(issue));
    }

    @Test
    @DisplayName("Validation isValidName(): 'Alice' -> true")
    void TestIsValidName1() {
        String name = "Alice";

        Assertions.assertTrue(Validation.isValidName(name));
    }

    @Test
    @DisplayName("Validation isValidName(): null -> false")
    void TestIsValidName2() {
        String name = null;

        Assertions.assertFalse(Validation.isValidName(name));
    }
}