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

    public User(String name, Gender gender) {
        this.userId = String.format("%06d", nextId++);
        this.name = name;
        this.gender = gender;
    }

    protected abstract int getBorrowingLimit();

    public boolean borrow(Item item) throws Exception {
        if (borrowedItems.size() >= getBorrowingLimit()) {
            throw new Exception("Borrow limit of " + getBorrowingLimit() + " reached for: " + name);
        } if (this instanceof Student && !(item instanceof Book)) {
            throw new Exception("Students can only borrow books. Got: " + item.getClass().getSimpleName());
        }
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
