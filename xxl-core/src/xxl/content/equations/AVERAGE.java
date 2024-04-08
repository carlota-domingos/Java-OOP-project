package xxl.content.equations;

import xxl.content.ValInt;
import xxl.gama.Cell;
import xxl.gama.Gama;

/**
 * The average of a gama's values.
 */
public class AVERAGE extends GamaEquation {
    public AVERAGE(Gama Gama){
        super(Gama);
    }

    public void setResult(){
        int total = 0;
        try{
            for(Cell cell : getGama().getCells()){
                total += ((ValInt)getfinalValue(cell.getContent())).getValue();
            }  
        } catch (NullPointerException | ArithmeticException |ClassCastException| NumberFormatException e){
            super.setResult(null);
            return;
        }
        super.setResult(new ValInt(total/ getGama().getCells().size()));
    }

    public String visualize(){
        setResult();
        return super.viewResult()+"=AVERAGE("+getGama().toString()+")";
    }
    
}
