package xxl.content.equations;

import xxl.content.Content;

/**
 * This class represents an equation.
 */
public abstract class Equation extends Content {
    private Content result= null;
    /**the result of the equation.*/

    public Equation(){super();}

    /**
     * Sets the result of the equation.
     */
    public abstract void setResult();
    /**
     * Updates the result and returns the visualization of the equation.
     * 
     * @return string
     */
    public abstract String visualize();

    public void setResult(Content result){ 
        this.result= result;
    }

    public Content getResult(){
        setResult();
        return result;
    }

    /**
     * Returns the string representation of the value of this content.
     */
    public String viewResult(){
        if (result == null)
            return "#VALUE";
        else
            return result.visualize();
    }
}
