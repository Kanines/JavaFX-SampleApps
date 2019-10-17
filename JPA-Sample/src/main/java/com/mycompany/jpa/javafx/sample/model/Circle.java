package com.mycompany.jpa.javafx.sample.model;

public enum Circle {

    INITIAL(0),
    FIRST(1),
    SECOND(5),
    THIRD(15);

    private int minLevel;

    private Circle(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }
}
