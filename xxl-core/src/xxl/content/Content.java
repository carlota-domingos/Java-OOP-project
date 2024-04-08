package xxl.content;
import java.io.Serializable;

import xxl.content.equations.Equation;

/**
 * This class represents a content.
 */
public abstract class Content implements Serializable {
    public Content(){}

    /**
     * Returns the string representation of value of this content.
     * @return string
     */
    public abstract String viewResult();

    /**
     * Returns the visualization of the content.
     * @return string
     */
    public abstract String visualize();

    /**
     * Returns the value of the content;
     * 
     * @param arg
     * @return content
     */
    public Content getfinalValue(Content arg) {
        try{
            while (true){
                try{
                    arg= ((RefCell)arg).getRefCell().getContent();
                } catch (ClassCastException e){
                    try{
                        arg= ((Equation)arg).getResult();
                    } catch (ClassCastException e2){
                        return arg;
                    }
                }
            }
        } catch (NullPointerException e){
            return null;
        }
        
    }

}