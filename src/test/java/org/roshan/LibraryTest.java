package org.roshan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class LibraryTest {
    @Test
    @DisplayName("Library add(): adding 'The Hobbit' to fresh library -> returns true and contains it")
    void TestLibraryAdd1() throws Exception {
        Library library = new Library();
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        boolean result = library.add(hobbit);

        Assertions.assertTrue(result);
        Assertions.assertTrue(library.getItems().contains(hobbit));
    }

    @Test
    @DisplayName("Library add(): adding null -> returns false, size stays 0")
    void TestLibraryAdd2() {
        Library library = new Library();

        boolean result = library.add(null);

        Assertions.assertFalse(result);
        Assertions.assertEquals(0, library.getItems().size());
    }

    @Test
    @DisplayName("Library remove(): removing 'Inception' (000002) -> returns true, no longer in library")
    void TestLibraryRemove1() throws Exception {
        Library library = new Library();
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");
        library.add(inception);

        boolean result = library.remove(inception);

        Assertions.assertTrue(result);
        Assertions.assertFalse(library.getItems().contains(inception));
    }

    @Test
    @DisplayName("Library remove(): removing null -> returns false")
    void TestLibraryRemove2() {
        Library library = new Library();

        boolean result = library.remove(null);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Library searchStream(): 'hobbit' -> finds 'The Hobbit' (000001)")
    void TestLibrarySearchStream1() throws Exception {
        Library library = new Library();
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");
        library.add(hobbit);

        List<Item> results = library.searchStream("hobbit");

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("The Hobbit", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Library searchStream(): 'nolan' (no title match) -> empty list")
    void TestLibrarySearchStream2() throws Exception {
        Library library = new Library();
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");
        library.add(inception);

        List<Item> results = library.searchStream("nolan");

        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Library searchRecursive(): 'atomic' -> finds 'Atomic Habits' (000004)")
    void TestLibrarySearchRecursive1() throws Exception {
        Library library = new Library();
        Book atomicHabits = new Book("Atomic Habits", Item.ItemStatus.AVAILABLE, "9780735211292", Book.Genre.SELF_HELP, "James Clear");
        atomicHabits.setId("000004");
        library.add(atomicHabits);

        List<Item> results = library.searchRecursive("atomic", library.getItems(), 0);

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Atomic Habits", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Library searchRecursive(): 'inter' -> finds 'Interstellar' (000005)")
    void TestLibrarySearchRecursive2() throws Exception {
        Library library = new Library();
        DVD interstellar = new DVD("Interstellar", Item.ItemStatus.AVAILABLE, "Christopher Nolan", 169);
        interstellar.setId("000005");
        library.add(interstellar);

        List<Item> results = library.searchRecursive("inter", library.getItems(), 0);

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Interstellar", results.get(0).getTitle());
    }
}