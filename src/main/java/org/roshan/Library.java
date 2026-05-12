package org.roshan;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Item> items;
    private List<User> users;

    public Library(String name) {
        this.name = name;
        items = new ArrayList<>();
        users = new ArrayList<>();
    }
}
