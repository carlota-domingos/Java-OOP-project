package xxl.content.equations;

import xxl.content.ValStr;
import xxl.gama.Cell;
import xxl.gama.Gama;

/**
 * The concatenation of a gama's String values.
 */
public class CONCAT extends GamaEquation {
    public CONCAT(Gama Gama){
        super(Gama);
    }


    public void setResult(){
        String result= "'";
        try{
            for(Cell cell : getGama().getCells()){
                try{
                    String string = ((ValStr)getfinalValue(cell.getContent())).visualize();
                    result += string.replaceFirst("'","");
                } catch (NullPointerException | ClassCastException e){
                    super.setResult(null);
                    return;
                }
            }  
        } catch (NullPointerException e){
            super.setResult(null);
            return;
        }
        super.setResult(new ValStr(result));
       
    }

    public String visualize(){
        setResult();
        return super.viewResult()+"=CONCAT("+getGama().toString()+")";
    }
}
