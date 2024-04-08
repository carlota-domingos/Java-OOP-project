package xxl.content.equations;

import xxl.content.Content;
import xxl.content.ValInt;

/**
 * The subtraction of two contents.
 */
public class SUB extends BinaryEquation {
    public SUB(Content arg1, Content arg2) {
        super(arg1, arg2);

    }

    public void setResult(){
        Content arg1= getArg1();
        Content arg2= getArg2();
        try{
            arg1= getfinalValue(arg1);
            arg2= getfinalValue(arg2);
            int result= ((ValInt)arg1).getValue() - ((ValInt)arg2).getValue();
            super.setResult(new ValInt(result));
        } catch (NullPointerException| NumberFormatException |ClassCastException e){
            super.setResult(null);
        }
    }

    @Override
    public String visualize(){
        setResult(); 
        return super.viewResult()+"=SUB("+getArg1().toString()+","+getArg2().toString()+")"; 
    }
}
