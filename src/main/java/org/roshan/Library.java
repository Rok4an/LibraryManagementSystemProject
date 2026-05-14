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

    /**
     * Loads items and users from CSV files into the library system.
     */
    public void loadData() {
        items = new ArrayList<>();
        users = new HashMap<>();

        try {
            // ITEMS
            Scanner scanner = new Scanner(new File(Constant.ITEMS_CSV_PATH));
            scanner.nextLine(); // header

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] element = line.split(",");

                String id = element[0];
                String type = element[1];
                String title = element[2];
                Item.ItemStatus status = Item.ItemStatus.valueOf(element[3]);

                Item item = null;

                if (type.equals("Book")) {
                    String isbn = element[4];
                    String author = element[5];
                    Book.Genre genre = Book.Genre.valueOf(element[6]);
                    item = new Book(title, status, isbn, genre, author);
                } else if (type.equals("DVD")) {
                    String director = element[4];
                    int duration = Integer.parseInt(element[5]);
                    item = new DVD(title, status, director, duration);
                } else if (type.equals("Magazine")) {
                    int issueNumber = Integer.parseInt(element[4]);
                    String publisher = element[5];
                    item = new Magazine(title, status, issueNumber, publisher);
                }

                if (item != null) {
                    if (Validation.isValidId(id)) {
                        item.setId(id);
                        items.add(item);
                    } else {
                        System.out.println("Invalid item ID skipped: " + id);
                    }
                }
            }

            scanner.close();

            // USERS
            Scanner console = new Scanner(new File(Constant.USERS_CSV_PATH));
            console.nextLine(); // header

            while (console.hasNext()) {
                String line = console.nextLine();
                String[] data = line.split(",");

                String id = data[0];
                String role = data[1];
                String name = data[2];
                User.Gender gender = User.Gender.valueOf(data[3]);

                List<Item> borrowedItem = new ArrayList<>();

                if (data.length > 4 && !data[4].isBlank()) {
                    String[] borrowedIds = data[4].split(";");
                    for (int i = 0; i < borrowedIds.length; i++) {
                        String borrowedId = borrowedIds[i];
                        for (int j = 0; j < items.size(); j++) {
                            Item item = items.get(j);
                            if (item.getId().equals(borrowedId)) {
                                borrowedItem.add(item);
                            }
                        }
                    }
                }

                User user = null;

                if (role.equals("STUDENT")) {
                    Student s = new Student(name, gender);
                    s.setBorrowedItems(borrowedItem);
                    user = s;
                } else if (role.equals("TEACHER")) {
                    Teacher t = new Teacher(name, gender);
                    t.setBorrowedItems(borrowedItem);
                    user = t;
                } else if (role.equals("ADMIN")) {
                    Admin a = new Admin(name, gender);
                    a.setBorrowedItems(borrowedItem);
                    user = a;
                } else {
                    System.out.println("Invalid user type");
                }

                if (user != null) {
                    if (Validation.isValidUniqueId(id)) {
                        user.setUserId(id);
                        users.put(id, user);
                    } else {
                        System.out.println("Invalid user ID skipped: " + id);
                    }
                }
            }

            console.close();

            System.out.println("Data loaded successfully.");

        } catch (IOException | InvalidNameException | InvalidIdException | InvalidISBNException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Saves all users and items back into CSV files.
     * This method preserves system state for reloading.
     */
    public void backupData() {
        try {
            // ITEMS
            FileWriter itemWriter = new FileWriter(Constant.ITEMS_CSV_PATH);
            itemWriter.write("id,type,title,status,isbn/dir/issue,author/duration/publisher,genre\n");

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String type = item.getClass().getSimpleName();

                if (item instanceof Book) {
                    Book b = (Book) item;
                    itemWriter.write(item.getId() + "," + type + "," + item.getTitle() + "," + item.getStatus() + ","
                            + b.getIsbn() + "," + b.getAuthor() + "," + b.getGenre() + "\n");
                } else if (item instanceof DVD) {
                    DVD d = (DVD) item;
                    itemWriter.write(item.getId() + "," + type + "," + item.getTitle() + "," + item.getStatus() + ","
                            + d.getDirector() + "," + d.getDuration() + "\n");
                } else if (item instanceof Magazine) {
                    Magazine m = (Magazine) item;
                    itemWriter.write(item.getId() + "," + type + "," + item.getTitle() + "," + item.getStatus() + ","
                            + m.getIssueNumber() + "," + m.getPublisher() + "\n");
                }
            }

            itemWriter.close();

            // USERS
            FileWriter userWriter = new FileWriter(Constant.USERS_CSV_PATH);
            userWriter.write("id,role,name,gender,borrowedItems\n");

            for (User user : users.values()) {
                String role;
                if (user instanceof Student) role = "STUDENT";
                else if (user instanceof Teacher) role = "TEACHER";
                else if (user instanceof Admin) role = "ADMIN";
                else role = "UNKNOWN";

                String borrowed = "";
                List<Item> borrowedItems = user.getBorrowedItems();
                for (int i = 0; i < borrowedItems.size(); i++) {
                    Item item = borrowedItems.get(i);
                    borrowed += item.getId();
                    if (i < borrowedItems.size() - 1) {
                        borrowed += ";";
                    }
                }

                userWriter.write(user.getUserId() + "," + role + "," + user.getName() + ","
                        + user.getGender() + "," + borrowed + "\n");
            }

            userWriter.close();

            System.out.println("Backup completed successfully.");

        } catch (IOException e) {
            System.out.println("Error while backing up data: " + e.getMessage());
        }
    }
}
