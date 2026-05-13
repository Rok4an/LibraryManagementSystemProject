package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)

public class Magazine extends Item {
    private int issueNumber;
    private String publisher;

    public Magazine(String title, ItemStatus status, int issueNumber, String publisher)
            throws InvalidIdException {
        super(title, status);
        if (!Validation.isValidIssueId(issueNumber)) {
            throw new InvalidIdException("Invalid issue number: " + issueNumber);
        }
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    @Override
    public int getBorrowLimit() {
        return 1;
    }
}
