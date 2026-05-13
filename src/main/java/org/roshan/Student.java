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

    public Student(String name, Gender gender)
            throws InvalidNameException, InvalidIdException {
        super(name, gender);

        this.studentId = String.format("%06d", nextId++);
        this.userId = "S" + this.studentId;

        if (!Validation.isValidUniqueId(this.userId)) {
            throw new InvalidIdException("Invalid student ID: " + this.userId);
        }
    }

    @Override
    public int getBorrowLimit() {
        return Constant.MAX_BOOKS_STUDENT;
    }

    @Override
    protected void validateBorrow(Item item) throws ForbiddenItemException {
        if (!(item instanceof Book)) {
            throw new ForbiddenItemException(
                    "Students can only borrow books. Tried to borrow: " + item.getTitle());
        }
    }
}
