package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class User implements Sortable {
    protected String userId;
    protected String name;
    protected Gender gender;
    protected List<Item> borrowedItems = new ArrayList<>();

    protected static int nextId = 1;

    public User(String name, Gender gender) throws InvalidNameException {
        if (!Validation.isValidName(name)) {
            throw new InvalidNameException("Invalid name: " + name);
        }
        this.name = name;
        this.gender = gender;
    }

    /**
     * Borrows an item if allowed and available.
     * @param item the item to borrow
     * @throws BorrowLimitExceededException if user reached borrowing limit
     * @throws ItemNotAvailableException    if item is not available
     */
    public void borrowItem(Item item)
            throws BorrowLimitExceededException, ItemNotAvailableException, ForbiddenItemException {

        if (item == null) return;

        if (borrowedItems.size() >= getBorrowLimit()) {
            throw new BorrowLimitExceededException(name + " reached borrowing limit.");
        }

        validateBorrow(item);

        item.borrowItem();
        borrowedItems.add(item);
    }

    /**
     * Returns an item if it was borrowed by this user.
     * @param item the item to return
     * @throws ItemNotBorrowedException if user did not borrow the item
     */
    public void returnItem(Item item) throws ItemNotBorrowedException {
        if (item == null || !borrowedItems.contains(item)) {
            throw new ItemNotBorrowedException(
                    "User '" + name + "' did not borrow item '" +
                            (item != null ? item.getTitle() : "null") + "'.");
        }
        item.returnItem();
        borrowedItems.remove(item);
    }

    protected abstract void validateBorrow(Item item) throws ForbiddenItemException;

    public abstract int getBorrowLimit();

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof User u)) return 0;

        return switch (this) {

            case Student s1 -> {
                if (u instanceof Student s2) {
                    yield s1.getStudentId().compareToIgnoreCase(s2.getStudentId());
                }
                yield this.name.compareToIgnoreCase(u.getName());
            }

            case Teacher t1 -> {
                if (u instanceof Teacher t2) {
                    yield t1.getTeacherId().compareToIgnoreCase(t2.getTeacherId());
                }
                yield this.name.compareToIgnoreCase(u.getName());
            }

            case Admin a1 -> {
                if (u instanceof Admin a2) {
                    yield a1.getAdminId().compareToIgnoreCase(a2.getAdminId());
                }
                yield this.name.compareToIgnoreCase(u.getName());
            }

            default -> this.name.compareToIgnoreCase(u.getName());
        };
    }

    public enum Gender {
        MALE,
        FEMALE
    }
}
