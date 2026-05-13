package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User {
    private String studentId;

    public Student(String name, List<Item> borrowedItems, Gender gender, String studentId) {
        super(name, borrowedItems, gender);
        this.studentId = studentId;
    }
}
