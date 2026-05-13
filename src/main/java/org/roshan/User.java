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

    public User(String name, Gender gender, String prefix)
            throws InvalidNameException, InvalidIdException {

        if (!Validation.isValidName(name)) {
            throw new InvalidNameException("Invalid name: " + name);
        }

        this.userId = prefix + String.format("%06d", nextId++);

        if (!Validation.isValidUniqueId(this.userId)) {
            throw new InvalidIdException("Generated ID is invalid: " + this.userId);
        }

        this.name = name;
        this.gender = gender;
    }

    protected abstract void validateBorrow(Item item) throws Exception;

    protected abstract int getBorrowLimit();

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

    public boolean returnItem(Item item) throws Exception {
        boolean removed = borrowedItems.remove(item);
        if (!removed) {
            throw new Exception("Item '" + item.getTitle() + "' not found in " + name + "'s list.");
        }
        item.returnItem();
        return true;
    }

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
