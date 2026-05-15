package org.roshan;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        library.loadData();
        System.out.println("Welcome to the Library Management System");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> borrowMenu();
                case "3" -> returnMenu();
                case "4" -> markLostMenu();
                case "5" -> adminMenu();
                case "6" -> {
                    library.backupData();
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Search for an item");
        System.out.println("2. Borrow an item");
        System.out.println("3. Return an item");
        System.out.println("4. Mark item as lost");
        System.out.println("5. Admin menu");
        System.out.println("6. Save & Exit");
        System.out.print("Enter choice: ");
    }

    private static void searchMenu() {
        System.out.println("\n===== SEARCH =====");
        System.out.println("1. Search by title (stream)");
        System.out.println("2. Search by title (recursive)");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim();

        List<Item> results;

        if (choice.equals("1")) {
            results = library.searchStream(keyword);
        } else {
            results = library.searchRecursive(keyword, library.getItems(), 0);
        }

        List<Item> deduplicated = results.stream()
                .filter(item -> results.stream()
                        .noneMatch(other -> other != item
                                && other.getTitle().equalsIgnoreCase(item.getTitle())))
                .toList();
        List<String> seen = new java.util.ArrayList<>();
        List<Item> unique = new java.util.ArrayList<>();
        for (Item item : results) {
            if (!seen.contains(item.getTitle().toLowerCase())) {
                seen.add(item.getTitle().toLowerCase());
                unique.add(item);
            }
        }

        if (unique.isEmpty()) {
            System.out.println("No items found for: " + keyword);
        } else {
            System.out.println("\nResults:");
            for (Item item : unique) {
                System.out.println("  [" + item.getId() + "] " + item.getTitle()
                        + " (" + item.getClass().getSimpleName() + ") - " + item.getStatus());
            }
        }
    }

    private static void borrowMenu() {
        System.out.println("\n===== BORROW ITEM =====");

        User user = selectUser();
        if (user == null) return;

        Item item = selectItem();
        if (item == null) return;

        try {
            user.borrowItem(item);
            System.out.println("Success! '" + item.getTitle() + "' borrowed by " + user.getName());
        } catch (BorrowLimitExceededException e) {
            System.out.println("Borrow limit reached: " + e.getMessage());
        } catch (ItemNotAvailableException e) {
            System.out.println("Item not available: " + e.getMessage());
        } catch (ForbiddenItemException e) {
            System.out.println("Not allowed: " + e.getMessage());
        }
    }

    private static void returnMenu() {
        System.out.println("\n===== RETURN ITEM =====");

        User user = selectUser();
        if (user == null) return;

        if (user.getBorrowedItems().isEmpty()) {
            System.out.println(user.getName() + " has no borrowed items.");
            return;
        }

        System.out.println("Borrowed items:");
        List<Item> borrowed = user.getBorrowedItems();
        for (int i = 0; i < borrowed.size(); i++) {
            System.out.println("  " + (i + 1) + ". [" + borrowed.get(i).getId() + "] " + borrowed.get(i).getTitle());
        }

        System.out.print("Enter item number to return: ");
        int index = parseIntInput(scanner.nextLine().trim()) - 1;
        if (index < 0 || index >= borrowed.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Item item = borrowed.get(index);
        try {
            user.returnItem(item);
            System.out.println("Success! '" + item.getTitle() + "' returned.");
        } catch (ItemNotBorrowedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void markLostMenu() {
        System.out.println("\n===== MARK ITEM AS LOST =====");

        Item item = selectItem();
        if (item == null) return;

        item.markLost();
        System.out.println("'" + item.getTitle() + "' has been marked as LOST.");
    }

    private static void adminMenu() {
        System.out.println("\n===== ADMIN MENU =====");

        Admin admin = null;
        for (User user : library.getUsers().values()) {
            if (user instanceof Admin a) {
                admin = a;
                break;
            }
        }

        if (admin == null) {
            System.out.println("No admin found in the system.");
            return;
        }

        System.out.println("1. Generate report");
        System.out.println("2. Add item to library");
        System.out.println("3. Remove item from library");
        System.out.println("4. Backup data to CSV");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> admin.generateReport(library.getItems());
            case "2" -> addItemMenu();
            case "3" -> removeItemMenu();
            case "4" -> library.backupData();
            default  -> System.out.println("Invalid choice.");
        }
    }

    private static void addItemMenu() {
        System.out.println("\n-- Add Item --");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        System.out.print("Enter type: ");
        String type = scanner.nextLine().trim();

        try {
            switch (type) {
                case "1" -> {
                    System.out.print("Title: ");       String title  = scanner.nextLine().trim();
                    System.out.print("ISBN (13 digits): "); String isbn = scanner.nextLine().trim();
                    System.out.print("Author: ");      String author = scanner.nextLine().trim();
                    System.out.println("Genre (ROMANCE/THRILLER/SCI_FI/FANTASY/MYSTERY/BIOGRAPHY/SELF_HELP/HISTORY): ");
                    Book.Genre genre = Book.Genre.valueOf(scanner.nextLine().trim().toUpperCase());
                    Book book = new Book(title, Item.ItemStatus.AVAILABLE, isbn, genre, author);
                    library.add(book);
                    System.out.println("Book added with ID: " + book.getId());
                }
                case "2" -> {
                    System.out.print("Title: ");        String title    = scanner.nextLine().trim();
                    System.out.print("Director: ");     String director = scanner.nextLine().trim();
                    System.out.print("Duration (min): "); int duration  = parseIntInput(scanner.nextLine().trim());
                    DVD dvd = new DVD(title, Item.ItemStatus.AVAILABLE, director, duration);
                    library.add(dvd);
                    System.out.println("DVD added with ID: " + dvd.getId());
                }
                case "3" -> {
                    System.out.print("Title: ");        String title     = scanner.nextLine().trim();
                    System.out.print("Issue Number: "); int issue        = parseIntInput(scanner.nextLine().trim());
                    System.out.print("Publisher: ");    String publisher = scanner.nextLine().trim();
                    Magazine mag = new Magazine(title, Item.ItemStatus.AVAILABLE, issue, publisher);
                    library.add(mag);
                    System.out.println("Magazine added with ID: " + mag.getId());
                }
                default -> System.out.println("Invalid type.");
            }
        } catch (Exception e) {
            System.out.println("Failed to add item: " + e.getMessage());
        }
    }

    private static void removeItemMenu() {
        System.out.println("\n-- Remove Item --");
        Item item = selectItem();
        if (item == null) return;

        boolean removed = library.remove(item);
        if (removed) {
            System.out.println("'" + item.getTitle() + "' removed from library.");
        } else {
            System.out.println("Could not remove item.");
        }
    }

    /**
     * Prints all users and lets the operator pick one by number.
     * @return the selected User, or null if invalid
     */
    private static User selectUser() {
        List<User> userList = new java.util.ArrayList<>(library.getUsers().values());
        if (userList.isEmpty()) {
            System.out.println("No users in the system.");
            return null;
        }

        System.out.println("Select a user:");
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            System.out.println("  " + (i + 1) + ". [" + u.getUserId() + "] "
                    + u.getName() + " (" + u.getClass().getSimpleName() + ")");
        }

        System.out.print("Enter user number: ");
        int index = parseIntInput(scanner.nextLine().trim()) - 1;
        if (index < 0 || index >= userList.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return userList.get(index);
    }

    /**
     * Prints all items and lets the operator pick one by number.
     * @return the selected Item, or null if invalid
     */
    private static Item selectItem() {
        List<Item> itemList = library.getItems();
        if (itemList.isEmpty()) {
            System.out.println("No items in the library.");
            return null;
        }

        System.out.println("Select an item:");
        for (int i = 0; i < itemList.size(); i++) {
            Item it = itemList.get(i);
            System.out.println("  " + (i + 1) + ". [" + it.getId() + "] "
                    + it.getTitle() + " (" + it.getClass().getSimpleName() + ") - " + it.getStatus());
        }

        System.out.print("Enter item number: ");
        int index = parseIntInput(scanner.nextLine().trim()) - 1;
        if (index < 0 || index >= itemList.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return itemList.get(index);
    }

    /**
     * Safely parses an integer, returns -1 if invalid.
     * @param input the string to parse
     * @return parsed int or -1
     */
    private static int parseIntInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}