package xxl.content.equations;

import xxl.content.Content;

/**
 * This class represents an equation whose arguments are contents.
 */
public abstract class BinaryEquation extends Equation {
    private Content arg1= null;
    /**the first argument of the equation.*/
    private Content arg2= null;
    /**the second argument of the equation.*/
    
    BinaryEquation(Content arg1, Content arg2){  
        this.arg1= arg1;
        this.arg2= arg2;
    }
    
    public Content getArg1() {
        return arg1;
    }
    public Content getArg2() {
        return arg2;
    }

}  
