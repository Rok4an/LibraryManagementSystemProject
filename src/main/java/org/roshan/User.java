package org.roshan;

import java.util.List;

public abstract class User implements Sortable {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }
}
