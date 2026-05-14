package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {

    private String teacherId;

    public Teacher(String name, Gender gender)
            throws InvalidNameException, InvalidIdException {
        super(name, gender);
        this.teacherId = String.format("%06d", nextId++);
        this.userId = "T" + this.teacherId;

        if (!Validation.isValidUniqueId(this.userId)) {
            throw new InvalidIdException("Invalid teacher ID: " + this.userId);
        }
    }

    @Override
    public int getBorrowLimit() {
        return Constant.MAX_ITEMS_TEACHER;
    }

    @Override
    protected void validateBorrow(Item item) {}
}
