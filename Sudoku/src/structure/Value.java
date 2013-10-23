package structure;

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

    public static Value characterToValue(char character)
    {
        switch(character)
        {
            case '1': return Value.ONE;
            case '2': return Value.TWO;
            case '3': return Value.THREE;
            case '4': return Value.FOUR;
            case '5': return Value.FIVE;
            case '6': return Value.SIX;
            case '7': return Value.SEVEN;
            case '8': return Value.EIGHT;
            case '9': return Value.NINE;
            default: return Value.EMPTY;
        }
    }

    public int toInt() {
        return value;
    }

    /**
     * Checks that a value is between 1 and 9 (inclusive) and is therefor a valid index for the board.
     * @param n The value being checked.
     * @return True if n is within a valid range, otherwise false.
     */
    private boolean isInRange(int n)
    {
        return ( (n >= 1) && (n <= 9) );
    }
}
