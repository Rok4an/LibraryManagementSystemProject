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

    public Student(String name, Gender gender, String studentId) throws Exception {
        super(name, gender);
        if (!Validation.isValidId(studentId)) {
            throw new Exception("Invalid student ID: " + studentId);
        }
        this.studentId = studentId;
    }
}
