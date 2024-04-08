package xxl.content.equations;

import xxl.content.ValInt;
import xxl.gama.Gama;
import xxl.gama.Cell;

/**
 * The product of a gama's integer values.
 */
public class PRODUCT extends GamaEquation{
    public PRODUCT(Gama Gama){
        super(Gama);
    }

    public void setResult(){
        int total = 1;
        try{
            for(Cell cell : getGama().getCells()){
                total *= ((ValInt)getfinalValue(cell.getContent())).getValue();
            }  
        } catch (NullPointerException | ArithmeticException | NumberFormatException |ClassCastException e){
            super.setResult(null);
            return;
        }
        super.setResult(new ValInt(total));
    }

    public String visualize(){
        setResult();
        return super.viewResult()+"=PRODUCT("+getGama().toString()+")";
    }
}
