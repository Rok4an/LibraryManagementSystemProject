package org.roshan;

import java.util.List;

public class Teacher extends User {
    private String employeeId;

    public Teacher(String name, List<Item> borrowedItems, Gender gender, String employeeId) {
        super(name, borrowedItems, gender);
        this.employeeId = employeeId;
    }
}
