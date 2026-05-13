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
    protected int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void validateBorrow(Item item) {
        // Can borrow any item
    }

    @Override
    public void generateReport(List<Item> items) {
        System.out.println("=== LIBRARY REPORT ===");
        for (Item item : items) {
            System.out.println(item.getStatus() + " | " + item);
        }
    }
}
