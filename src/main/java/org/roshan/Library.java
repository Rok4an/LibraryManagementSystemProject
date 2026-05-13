package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString

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
