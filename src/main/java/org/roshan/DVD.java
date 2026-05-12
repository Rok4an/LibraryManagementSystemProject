package org.roshan;

public class DVD extends Item {
    private String director;
    private int    duration;

    public DVD(String title, ItemStatus status, String director, int duration) {
        super(title, status);
        this.director = director;
        this.duration = duration;
    }
}
