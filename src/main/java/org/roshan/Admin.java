package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User implements Reportable {
    private String adminId;

    public Admin(String name, Gender gender)
            throws InvalidNameException, InvalidIdException {
        super(name, gender);
        this.adminId = String.format("%06d", nextId++);
        this.userId = "A" + this.adminId;

        if (!Validation.isValidUniqueId(this.userId)) {
            throw new InvalidIdException("Invalid admin ID: " + this.userId);
        }
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void validateBorrow(Item item) {
        // Admins can borrow any item type
    }

    /**
     * Generates a report that shows every item grouped by status.
     * @param items the list of items to include in the report
     */
    @Override
    public void generateReport(List<Item> items) {
        System.out.println("LIBRARY STATUS REPORT\n");

        System.out.println("BORROWED ITEMS:");
        List<Item> borrowedItems = items.stream()
                .filter(item -> item.getStatus() == Item.ItemStatus.BORROWED)
                .distinct()
                .toList();
        System.out.println(borrowedItems);

        System.out.println("\nAVAILABLE ITEMS:");
        List<Item> availableItems = items.stream()
                .filter(item -> item.getStatus() == Item.ItemStatus.AVAILABLE)
                .distinct()
                .toList();
        System.out.println(availableItems);

        System.out.println("\nLOST ITEMS:");
        List<Item> lostItems = items.stream()
                .filter(item -> item.getStatus() == Item.ItemStatus.LOST)
                .distinct()
                .toList();
        System.out.println(lostItems);
    }

    /**
     * Converts information to CSV format.
     * @return a string of information in CSV format
     */
    public String toCSV() {
        String borrowed = "";
        for (int i = 0; i < borrowedItems.size(); i++) {
            Item item = borrowedItems.get(i);
            borrowed += item.getId();
            if (i < borrowedItems.size() - 1) {
                borrowed += ";";
            }
        }
        return userId + "," + name + ",ADMIN," + gender + "," + borrowed + ",,";
    }
}
