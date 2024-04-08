package xxl.content.equations;

import xxl.content.ValStr;
import xxl.gama.Cell;
import xxl.gama.Gama;


/**
 * The first String value of a gama.
 */
public class COALESCE extends GamaEquation {
    public COALESCE(Gama Gama){
        super(Gama);
    }

    public void setResult(){
        try{
            for(Cell cell : getGama().getCells()){
                try{
                    String first = getfinalValue(cell.getContent()).visualize();
                    if(first.indexOf("'")==0){
                        super.setResult(new ValStr(first));
                        return;
                    }       
                } catch (NullPointerException | ClassCastException e){

                }
       
            }   
            super.setResult(null);
        } catch (NullPointerException e){
            super.setResult(null);
            return;
        }
       
    }

    public String viewResult(){
        setResult();
        return super.viewResult();
    }
    
    public String visualize(){
        setResult();
        return viewResult()+"=COALESCE("+getGama().toString()+")";
    }
    
}
