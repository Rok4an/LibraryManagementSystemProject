package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Teacher extends User {
    private String employeeId;

    public Teacher(String name, List<Item> borrowedItems, Gender gender, String employeeId) {
        super(name, borrowedItems, gender);
        this.employeeId = employeeId;
    }
}
