package xxl.content;

/**
 * This class represents a string value.
 */
public class ValStr extends Content {
    private String value = null;

    public ValStr(String value) {
        this.value = value;
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
        return value;
    }
    
}
