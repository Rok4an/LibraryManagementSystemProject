package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString

public class Library {
    private String name;
    private List<Item> items = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Library(String name) {
        this.name = name;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public void addUser(User user){
        users.add(user);
    }

    public void sortItems() {
        Collections.sort(items, Item::compareTo);
    }

    /**
     * Searches for items whose titles contain the given query using streams.
     * @param query the search query
     * @return a list of matching items
     */
    public List<Item> searchByTitleStream(String query) {
        String q = query.toLowerCase();
        return items.stream()
                .filter(i -> i.getTitle().toLowerCase().contains(q))
                .toList();
    }

    /**
     * Searches for items whose titles contain the given query using recursion.
     * @param query the search query
     * @param list the list of items to search
     * @param index the current index in the list
     * @return a list of matching items
     */
    public List<Item> searchByTitleRecursive(String query, List<Item> list, int index) {
        List<Item> result = new ArrayList<>();
        searchByTitleRecursiveHelper(query.toLowerCase(), list, index, result);
        return result;
    }

    /**
     * Helper method for recursive title search.
     * @param q the lowercase query
     * @param list the list of items
     * @param index the current index
     * @param result the list to store matches
     */
    private void searchByTitleRecursiveHelper(String q, List<Item> list, int index, List<Item> result) {
        if (index >= list.size()) return;
        Item it = list.get(index);
        if (it.getTitle().toLowerCase().contains(q)) {
            result.add(it);
        }
        searchByTitleRecursiveHelper(q, list, index + 1, result);
    }
}
