package org.roshan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("Student borrowItem(): Alice borrows 'Interstellar' (AVAILABLE) -> borrowedItems contains it")
    void TestStudentBorrowItem1() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);
        DVD interstellar = new DVD("Interstellar", Item.ItemStatus.AVAILABLE, "Christopher Nolan", 169);
        interstellar.setId("000005");

        // Act
        alice.borrowItem(interstellar);

        // Assert
        Assertions.assertTrue(alice.getBorrowedItems().contains(interstellar));
    }

    @Test
    @DisplayName("Student borrowItem(): Alice tries to borrow 'Inception' (BORROWED) -> throws ItemNotAvailableException")
    void TestStudentBorrowItem2() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");

        // Act & Assert
        Assertions.assertThrows(ItemNotAvailableException.class, () -> alice.borrowItem(inception));
    }

    @Test
    @DisplayName("Student returnItem(): Alice returns 'Inception' she borrowed -> borrowedItems no longer contains it")
    void TestStudentReturnItem1() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        inception.setId("000002");
        alice.getBorrowedItems().add(inception); // simulate already borrowed from CSV

        // Act
        alice.returnItem(inception);

        // Assert
        Assertions.assertFalse(alice.getBorrowedItems().contains(inception));
        Assertions.assertEquals(Item.ItemStatus.AVAILABLE, inception.getStatus());
    }

    @Test
    @DisplayName("Student returnItem(): Alice tries to return 'The Hobbit' she never borrowed -> throws ItemNotBorrowedException")
    void TestStudentReturnItem2() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        // Act & Assert
        Assertions.assertThrows(ItemNotBorrowedException.class, () -> alice.returnItem(hobbit));
    }

    @Test
    @DisplayName("Student getBorrowLimit(): Alice -> " + Constant.MAX_BOOKS_STUDENT)
    void TestStudentBorrowLimit1() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);

        // Act & Assert
        Assertions.assertEquals(Constant.MAX_BOOKS_STUDENT, alice.getBorrowLimit());
    }

    @Test
    @DisplayName("Student borrowItem(): exceeding borrow limit (5) -> throws BorrowLimitExceededException")
    void TestStudentBorrowLimit2() throws Exception {
        // Arrange
        Student alice = new Student("Alice", User.Gender.MALE);

        // Fill alice up to exactly the limit
        for (int i = 0; i < Constant.MAX_BOOKS_STUDENT; i++) {
            DVD filler = new DVD("Filler " + i, Item.ItemStatus.AVAILABLE, "Dir", 90);
            alice.borrowItem(filler);
        }

        DVD oneMore = new DVD("One Too Many", Item.ItemStatus.AVAILABLE, "Dir", 90);

        // Act & Assert
        Assertions.assertThrows(BorrowLimitExceededException.class, () -> alice.borrowItem(oneMore));
    }

    @Test
    @DisplayName("Teacher getBorrowLimit(): Mr.Brown -> " + Constant.MAX_ITEMS_TEACHER)
    void TestTeacherBorrowLimit1() throws Exception {
        // Arrange
        Teacher teacher = new Teacher("Mr.Brown", User.Gender.MALE);

        // Act & Assert
        Assertions.assertEquals(Constant.MAX_ITEMS_TEACHER, teacher.getBorrowLimit());
    }

    @Test
    @DisplayName("Teacher borrowItem(): Mr.Brown borrows 'The Hobbit' (AVAILABLE) -> borrowedItems contains it")
    void TestTeacherBorrowItem2() throws Exception {
        // Arrange
        Teacher teacher = new Teacher("Mr.Brown", User.Gender.MALE);
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");
        hobbit.setId("000001");

        // Act
        teacher.borrowItem(hobbit);

        // Assert
        Assertions.assertTrue(teacher.getBorrowedItems().contains(hobbit));
    }

    @Test
    @DisplayName("Admin getBorrowLimit(): Clara -> Integer.MAX_VALUE (unlimited)")
    void TestAdminBorrowLimit1() throws Exception {
        // Arrange
        Admin clara = new Admin("Clara", User.Gender.FEMALE);

        // Act & Assert
        Assertions.assertEquals(Integer.MAX_VALUE, clara.getBorrowLimit());
    }

    @Test
    @DisplayName("Admin generateReport(): runs on CSV items without exception")
    void TestAdminGenerateReport2() throws Exception {
        // Arrange
        Admin clara = new Admin("Clara", User.Gender.FEMALE);
        DVD inception = new DVD("Inception", Item.ItemStatus.BORROWED, "Christopher Nolan", 148);
        Book hobbit = new Book("The Hobbit", Item.ItemStatus.AVAILABLE, "9780547928227", Book.Genre.FANTASY, "J.R.R. Tolkien");

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> clara.generateReport(java.util.List.of(inception, hobbit)));
    }
}