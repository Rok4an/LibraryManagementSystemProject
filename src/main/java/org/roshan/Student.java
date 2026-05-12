package org.roshan;

import java.util.List;

public class Student extends User {
    private String studentId;

    public Student(String name, List<Item> borrowedItems, Gender gender, String studentId) {
        super(name, borrowedItems, gender);
        this.studentId = studentId;
    }
}
