package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    private List<Item> items = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();

    /**
     * Recursively searches for items whose title contains the given query.
     * @param query the keyword
     * @param items the list of items
     * @param index the index of the item
     * @return a list of items matching the keyword
     */
    public List<Item> searchRecursive(String query, List<Item> items, int index) {
        List<Item> result = new ArrayList<>();

        if (items == null || index >= items.size()) {
            return result;
        }

        Item currentItem = items.get(index);

        if (currentItem.getTitle().toLowerCase().contains(query.toLowerCase())) {
            result.add(currentItem);
        }

        result.addAll(searchRecursive(query, items, index + 1));
        return result;
    }

    /**
     * Searches items using a keyword in the title.
     * @param query the keyword
     * @return list of matching items
     */
    public List<Item> searchStream(String query) {
        if (query == null || query.isEmpty()) {
            return List.of();
        }

        String keyword = query.toLowerCase();

        return items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword))
                .toList();
    }

    /**
     * Adds an item to the library.
     * @param item the item to add
     * @return true if the item was added, false otherwise
     */
    public boolean add(Item item) {
        if (item == null) {
            return false;
        }

        if (!Validation.isValidId(item.getId())) {
            System.out.println("Invalid item ID");
            return false;
        }

        return items.add(item);
    }

    /**
     * Removes an item from the library.
     * @param item the item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean remove(Item item) {
        if (item == null) {
            return false;
        }

        return items.remove(item);
    }
}
