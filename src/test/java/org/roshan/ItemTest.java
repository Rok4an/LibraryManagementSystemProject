package org.roshan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {
    @Test
    @DisplayName("Item borrowItem(): 'The Hobbit' (AVAILABLE) -> status becomes BORROWED")
    void TestItemBorrowItem1() throws Exception {
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        hobbit.borrowItem();

        Assertions.assertEquals(Item.ItemStatus.BORROWED, hobbit.getStatus());
    }

    @Test
    @DisplayName("Item borrowItem(): 'Inception' (already BORROWED) -> throws ItemNotAvailableException")
    void TestItemBorrowItem2() throws Exception {
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");

        Assertions.assertThrows(ItemNotAvailableException.class, inception::borrowItem);
    }

    @Test
    @DisplayName("Item returnItem(): 'Inception' (BORROWED) -> status becomes AVAILABLE")
    void TestItemReturnItem1() throws Exception {
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");

        inception.returnItem();

        Assertions.assertEquals(Item.ItemStatus.AVAILABLE, inception.getStatus());
    }

    @Test
    @DisplayName("Item returnItem(): 'The Hobbit' (AVAILABLE) -> throws ItemNotBorrowedException")
    void TestItemReturnItem2() throws Exception {
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        Assertions.assertThrows(ItemNotBorrowedException.class, hobbit::returnItem);
    }

    @Test
    @DisplayName("Item markLost(): 'Interstellar' (AVAILABLE) -> status becomes LOST")
    void TestItemMarkLost1() throws Exception {
        DVD interstellar = new DVD("Interstellar", Item.ItemStatus.AVAILABLE, "Christopher Nolan", 169);
        interstellar.setId("000005");

        interstellar.markLost();

        Assertions.assertEquals(Item.ItemStatus.LOST, interstellar.getStatus());
    }

    @Test
    @DisplayName("Item markLost(): 'National Geographic' (already LOST) -> remains LOST")
    void TestItemMarkLost2() throws Exception {
        Magazine natGeo = new Magazine("National Geographic", Item.ItemStatus.LOST, 202, "National Geographic Society");
        natGeo.setId("000003");

        natGeo.markLost();

        Assertions.assertEquals(Item.ItemStatus.LOST, natGeo.getStatus());
    }

    @Test
    @DisplayName("Book getBorrowLimit(): 'The Hobbit' -> 1")
    void TestBookBorrowLimit1() throws Exception {
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        Assertions.assertEquals(1, hobbit.getBorrowLimit());
    }

    @Test
    @DisplayName("DVD getBorrowLimit(): 'Inception' -> 1")
    void TestDVDBorrowLimit2() throws Exception {
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");

        Assertions.assertEquals(1, inception.getBorrowLimit());
    }

    @Test
    @DisplayName("Book constructor: ISBN '123' (too short) -> throws InvalidISBNException")
    void TestBookInvalidISBN1() {
        Assertions.assertThrows(InvalidISBNException.class, () ->
                new Book("Bad Book", Item.ItemStatus.AVAILABLE, "123", Book.Genre.FANTASY, "Author"));
    }

    @Test
    @DisplayName("Magazine constructor: issueNumber 0 -> throws InvalidIdException")
    void TestMagazineInvalidIssue2() {
        Assertions.assertThrows(InvalidIdException.class, () ->
                new Magazine("Bad Magazine", Item.ItemStatus.AVAILABLE, 0, "Publisher"));
    }
}