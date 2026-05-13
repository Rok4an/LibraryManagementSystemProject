package org.roshan;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public abstract class User implements Sortable {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems = new ArrayList<>();
    protected Gender gender;
    protected static int nextId = 1;

    public User(String name, Gender gender) throws InvalidNameException {
        if (!Validation.isValidName(name)) {
            throw new InvalidNameException("Invalid name: " + name);
        }
        this.name = name;
        this.gender = gender;
    }

    protected abstract void validateBorrow(Item item) throws Exception;

    protected abstract int getBorrowLimit();

    /**
     * Borrows the given item for this user if allowed.
     * @param item the item to borrow
     * @return true if the borrowing succeeds
     * @throws BorrowLimitExceededException if the user reached the borrowing limit
     * @throws ItemNotAvailableException if the item is not available
     * @throws ForbiddenItemException if the user is not allowed to borrow this item type
     * @throws Exception if validation fails for other reasons
     */
    public boolean borrow(Item item)
            throws BorrowLimitExceededException,
            ItemNotAvailableException,
            ForbiddenItemException,
            Exception {

        if (borrowedItems.size() >= getBorrowLimit()) {
            throw new BorrowLimitExceededException(name + " reached borrowing limit.");
        }

        validateBorrow(item);

        item.borrowItem();
        borrowedItems.add(item);
        return true;
    }

    /**
     * Returns the given item for this user if it was borrowed.
     * @param item the item to return
     * @return true if the return succeeds
     * @throws ItemNotBorrowedException if the user did not borrow the item
     */
    public boolean returnItem(Item item) throws ItemNotBorrowedException {
        if (!borrowedItems.contains(item)) {
            throw new ItemNotBorrowedException(
                    "User '" + name + "' did not borrow item '" + item.getTitle() + "'.");
        }
        item.returnItem();
        borrowedItems.remove(item);
        return true;
    }

    /**
     * Searches for a borrowed item whose title contains the query.
     *
     * @param query the text to search for
     * @return the first matching item, or null if none found
     */
    public Item searchItem(String query) {
        return borrowedItems.stream()
                .filter(i -> i.getTitle().toLowerCase().contains(query.toLowerCase()))
                .findFirst()
                .orElse(null);
    }


    @Override
    public int compareTo(Object other) {
        if (!(other instanceof User u)) return 0;

        if (this instanceof Student && u instanceof Student) {
            Student s1 = (Student) this;
            Student s2 = (Student) u;
            return s1.getStudentId().compareToIgnoreCase(s2.getStudentId());
        }

        if (this instanceof Teacher && u instanceof Teacher) {
            Teacher t1 = (Teacher) this;
            Teacher t2 = (Teacher) u;
            return t1.getEmployeeId().compareToIgnoreCase(t2.getEmployeeId());
        }

        if (this instanceof Admin && u instanceof Admin) {
            Admin a1 = (Admin) this;
            Admin a2 = (Admin) u;
            return a1.getAdminId().compareToIgnoreCase(a2.getAdminId());
        }

        return this.name.compareToIgnoreCase(u.getName());
    }

    public enum Gender {
        MALE,
        FEMALE
    }
}
