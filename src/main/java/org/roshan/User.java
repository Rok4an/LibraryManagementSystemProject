package org.roshan;

import lombok.Getter;

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
        this.borrowedItems = borrowedItems;
        this.gender = gender;
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
