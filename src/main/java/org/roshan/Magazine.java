package org.roshan;

public class Magazine extends Item {
    private int issueNumber;
    private String publisher;

    public Magazine(String title, ItemStatus status, String publisher, int issueNumber) {
        super(title, status);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }
}
