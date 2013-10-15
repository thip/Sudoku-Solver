package com.company;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public enum Value{
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int value;

    private Value(final int value)
    {
        this.value = value;
    }

    public int toInt() { return value; }
}
