package xxl.gama;
import java.io.Serializable;
import java.util.ArrayList;

import xxl.Storage;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * This class represents a Gama.
 */
public class Gama implements Serializable{
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    /**The List of this gama's cell */

    public Gama(){}
    
    public Gama(Cell cell1, Cell cell2, Storage storage) throws UnrecognizedEntryException{
        super();
        if (storage.containsCellStorage(cell1)==false || storage.containsCellStorage(cell2)==false){
            throw new UnrecognizedEntryException("this cell");
        } else if (cell1.getline()!=cell2.getline() && cell1.getcolumn()!=cell2.getcolumn()){
            throw new UnrecognizedEntryException("this Gama");
        } else {
            if (cell1.getline()==cell2.getline()){
                createHorizontalGama(cell1, cell2, storage);
            } else {
                createVerticalGama(cell1, cell2,  storage);
            }
        }
        
    }

    /**
     * Creates a Gama from a range specification.
     * 
     * @param cell1
     * @param cell2
     * @param storage
     */
    public Gama(String cell1, String cell2, Storage storage) throws UnrecognizedEntryException{
        this(storage.getCellStorage(cell1), storage.getCellStorage(cell2), storage);  
    }

    /**
     * Creates an Horizontal Gama.
     * 
     * @param cell1
     * @param cell2
     * @param storage
     */
    private void createHorizontalGama(Cell cell1, Cell cell2, Storage storage){
        try{
            if (cell1.getcolumn()<cell2.getcolumn()){
                for (int i=cell1.getcolumn(); i<=cell2.getcolumn(); i++){
                    cells.add(storage.getCellStorage(cell1.getline()+";"+i));
                }
            } else {
                for (int i=cell2.getcolumn(); i<=cell1.getcolumn(); i++){
                    cells.add(storage.getCellStorage(cell1.getline()+";"+i));
                }
            }
        } catch (UnrecognizedEntryException e){

        }
    }

    /**
     * Creates a Vertical Gama.
     * 
     * @param cell1
     * @param cell2
     * @param storage
     */
    private void createVerticalGama(Cell cell1, Cell cell2, Storage storage){
        try{
            if (cell1.getline()<cell2.getline()){;
                for (int i=cell1.getline(); i<=cell2.getline(); i++){
                    cells.add(storage.getCellStorage(i+";"+cell1.getcolumn()));
                }
            } else {
                for (int i=cell2.getline(); i<=cell1.getline(); i++){
                    cells.add(storage.getCellStorage(i+";"+cell1.getcolumn()));
                }
            }
        } catch (UnrecognizedEntryException e){
        }
        
    }

    public ArrayList<Cell> getCells(){
        return cells;
    }

    /**
     * Adds a cell to this Gama.
     * 
     * @param cell
     */
    public void addCell(Cell cell){
        cells.add(cell);
    }

    /**
     * Returns true if the Gama is horizontal.
     * 
     * @return boolean
     */
    public boolean  isHorizontal(){
        return cells.get(0).getline()==cells.get(cells.size()-1).getline();
    }

    /**
     * Returns the visualization of the Gama and its content.
     * 
     * @return finalsstr
     */
    public String visualize(){ 
        String finalstr ="";
        for(int i=0 ; i< cells.size(); i++){
           finalstr += cells.get(i).visualize();
            if (i!=cells.size()-1){
               finalstr += "\n";
            }
        }
        return finalstr;
    }

    @Override
    public String toString() {
        return cells.get(0).toString() + ":" + cells.get(cells.size()-1).toString();
    }

}
