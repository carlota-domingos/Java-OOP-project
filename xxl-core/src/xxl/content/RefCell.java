package xxl.content;

import xxl.gama.Cell;

/**
 * This class represents a reference to a cell.
 */
public class RefCell extends Content {
    private Cell cell;
    /**The cell associeted to this reference*/

    public RefCell(Cell cell) {
        this.cell = cell;
    }
    
    public Cell getRefCell() {
        return cell;
    }

    @Override
    public String viewResult() {
        Content cellContent = cell.getContent();
        try{
            cellContent = getfinalValue(cellContent);
            return cellContent.visualize();
        } catch (NullPointerException e){
            return "#VALUE";
        }          
 
    }

    @Override
    public String visualize() {
        return viewResult()+"="+ cell.toString();
    }

    @Override
    public String toString() {
        return cell.toString();
    }
    
}