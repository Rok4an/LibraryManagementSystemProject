package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Admin extends User implements Reportable {
    private String  adminId;
    private Library library;

    public Admin(String name, List<Item> borrowedItems, Gender gender, String adminId, Library library) {
        super(name, borrowedItems, gender);
        this.adminId = adminId;
        this.library = library;
    }

    @Override
    protected int getBorrowingLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void generateReport(List<Item> items) {
        System.out.println("=== LIBRARY REPORT ===");
        for (Item item : items) {
            System.out.println(item.getStatus() + " | " + item);
        }
    }
}
