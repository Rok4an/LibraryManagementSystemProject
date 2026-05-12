package org.roshan;

import java.util.List;

public class Admin extends User implements Reportable {
    private String  adminId;
    private Library library;

    public Admin(String name, List<Item> borrowedItems, Gender gender, String adminId, Library library) {
        super(name, borrowedItems, gender);
        this.adminId = adminId;
        this.library = library;
    }
}
