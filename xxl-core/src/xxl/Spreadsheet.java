package xxl;

import java.io.Serial;
import java.io.Serializable;

import xxl.exceptions.UnrecognizedEntryException;
import xxl.gama.Cell;
import xxl.content.Content;
import xxl.gama.Gama;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;

    private User user = null;
    /** The current user. */
    private CutBuffer cutbuffer = null;
    /** The spreadsheet's cutbuffer. */
    private Storage storage;
    /** The spreadsheet's cell storage. */
    private boolean changed = false;
    /** Were there any changes made to the spreadsheet? */

    public Spreadsheet(int lines, int columns, User user){
        storage = new Storage(lines, columns);
        cutbuffer = new CutBuffer(storage);
        this.user = user;
    }

    /**
     * @return the current changed status.
     */
    public boolean changesMade(){
        return changed;
    }

    /**
     * set the changed status.
     * 
     * @param changed
     */
    public void setChanged(boolean changed){
        this.changed = changed;
    }

    /**
     * Returns the spreadsheet's cell storage.
     */
    public Storage getStorage(){
        return storage;
    }

    /**
     * Insert specified content in specified range.
     *
     * @param rangeSpecification
     * @param contentSpecification
     */
    public void insertContents(String rangeSpecification, String contentSpecification) throws UnrecognizedEntryException  {
        Content content = storage.getContent(contentSpecification);
        Gama gama = getGama(rangeSpecification);
        for (Cell cell : gama.getCells()) {
            if(content != null)
                cell.setContent(content);
        }
        this.changed = true;
    }

    /**
     * Show specified range.
     * @param gamaStr
     * @return String
     * @throws UnrecognizedEntryException
     */
    public String Show(String gamaStr) throws UnrecognizedEntryException{
        Gama gama = getGama(gamaStr);
        return "number of cells: "+ (gama.getCells()).size()+ '\n' + gama.visualize();
       
    }

    /**
     * Returns the gama of the specified range described in the prompt.
     * 
     * @param range
     * @return
     * @throws UnrecognizedEntryException
     */
        public Gama getGama(String range) throws UnrecognizedEntryException{
        if (range.contains(":")){
            String[] cells = range.split("[:]");
            Cell cell1 = storage.getCellStorage(cells[0]);
            Cell cell2 = storage.getCellStorage(cells[1]);
            return new Gama(cell1, cell2, storage);
        } else {
            return new Gama(storage.getCellStorage(range), storage.getCellStorage(range), storage);
        }      
    }

    /**
     * @return the spreadsheet's cutbuffer's representation.
     */
    public String visualizeCutBuffer(){
        return cutbuffer.visualize();
    }

    /**
     * Copy specified range to cutbuffer.
     * 
     * @param rangeSpecification
     * @throws UnrecognizedEntryException
     */
    public void copy(String rangeSpecification) throws UnrecognizedEntryException{
        cutbuffer.addtoCutBuffer(rangeSpecification);
    }

    /**
     * Paste specified range onto the spreadsheet.
     * 
     * @param rangeSpecification
     * @throws UnrecognizedEntryException
     */
    public void paste(String rangeSpecification) throws UnrecognizedEntryException{
        Gama gama = getGama(rangeSpecification);
        cutbuffer.paste(gama);
        changed = true;  
    }
    
    /**
     * Delete specified range.
     * 
     * @param rangeSpecification
     * @throws UnrecognizedEntryException
     */
    public void delete(String rangeSpecification) throws UnrecognizedEntryException {
        Gama gama = getGama(rangeSpecification);
        for (Cell cell : gama.getCells()) {
            cell.clear();
        }
        this.changed = true;
    }

    /**
     * Shows the instances of the functions containing the prompt.
     * 
     * @param contentSpecification
     * @return string 
     */
    public String ShowFunction(String contentSpecification) {
        Gama content = storage.findFunction(contentSpecification);
        return content.visualize();
    }

    /**
     * Shows the instances of the specified value.
     * 
     * @param contentSpecification
     * @return string
     */
    public String ShowValue(String contentSpecification){
        Gama content = storage.findValue(contentSpecification);
        return content.visualize();
    }

    public String ShowEvenValues(){
        Gama content = storage.findEvenValues();
        return content.visualize();
    }
    
}
