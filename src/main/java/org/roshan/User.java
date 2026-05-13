package org.roshan;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class User implements Sortable {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;
    private static int nextId = 1;

    public User(String name, List<Item> borrowedItems, Gender gender) {
        this.userId = String.format("%04d", nextId++);
        this.name = name;
        this.borrowedItems = new ArrayList<>();
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
        if (other instanceof User otherUser) {
            return this.name.compareToIgnoreCase(otherUser.getName());
        }
        return 0;
    }


    public enum Gender {
        MALE,
        FEMALE
    }
}
