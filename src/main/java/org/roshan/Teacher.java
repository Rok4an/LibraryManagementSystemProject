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

    public Teacher(String name, Gender gender)
            throws InvalidNameException, InvalidIdException {
        super(name, gender);

        this.employeeId = String.format("%06d", nextId++);
        this.userId = "T" + this.employeeId;

        if (!Validation.isValidUniqueId(this.userId)) {
            throw new InvalidIdException("Invalid employee ID: " + this.userId);
        }
    }

    public int getBorrowLimit() {
        return Constant.MAX_ITEMS_TEACHER;
    }

    @Override
    protected void validateBorrow(Item item){
        // Can borrow anything
    }
}
