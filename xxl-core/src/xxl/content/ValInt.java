package xxl.content;

/**
 * This class represents an integer value.
 */
public class ValInt extends Content {
    private int value;
    /**the integer associated.*/

    public ValInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String visualize() {
        return this.toString();
    }
    
    @Override 
    public String viewResult() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
