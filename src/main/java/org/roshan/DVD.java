package org.roshan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)

public class DVD extends Item {
    private String director;
    private int    duration;

    public DVD(String title, ItemStatus status, String director, int duration) {
        super(title, status);
        this.director = director;
        this.duration = duration;
    }
}
