package xxl;

import java.util.ArrayList;
import java.util.TreeMap;

import java.io.Serializable;

import xxl.content.Content;
import xxl.content.RefCell;
import xxl.content.ValInt;
import xxl.content.ValStr;
import xxl.content.equations.Equation;
import xxl.content.equations.ADD;
import xxl.content.equations.DIV;
import xxl.content.equations.MUL;
import xxl.content.equations.SUB;
import xxl.content.equations.AVERAGE;
import xxl.content.equations.COALESCE;
import xxl.content.equations.CONCAT;
import xxl.content.equations.PRODUCT;
import xxl.gama.Gama;
import xxl.gama.Cell;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Class representing the storage of cells of a spreadsheet.
 */
public class Storage implements Serializable{

    private TreeMap<String, Cell> cells;
    /** The TreeMap containing all cells of this storage. */
    private int lines;
    /** number of lines of the spreadsheet. */
    private int columns;
    /** number of columns of the spreadsheet. */
    
    Storage(int lines, int columns){
        this.lines= lines;
        this.columns= columns;
        cells = new TreeMap<String,Cell>();
        for (int i=1; i<=lines; i++){
            for (int j=1; j<=columns; j++){
                cells.put(i+";"+j, new Cell(i, j));
            }
        }
    }

    public int getLines(){
        return lines;
    }

    public int getColumns(){
        return columns;
    }
    
    /**
     * Returns the cell at the specified position in this storage.
     * 
     * @param cellkey
     * @return Cell
     */
    public Cell getCellStorage(String cellkey) throws UnrecognizedEntryException{
        Cell cell = cells.get(cellkey);
        if (cell != null){
            return cell;
        } else {
            throw new UnrecognizedEntryException(cellkey);
        }
    }

    /**
     * Updates a Cell with the specified content.
     * 
     * @param cellkey
     * @param content
     */
    public void updateCell(String cellkey, Content content){
        Cell cell = cells.get(cellkey);
        cell.setContent(content);
    }

    /**
     * Returns true if the cell is in the storage.
     * 
     * @param cell
     * @return Boolean
     */
    public Boolean containsCellStorage(Cell cell){
        return cell.getline()<=lines && cell.getcolumn()<=columns && cell.getline()>0 && cell.getcolumn()>0 ;
    }

    /**
     * Returns the equations of the specified range.
     * 
     * @param contentSpecification
     * @return Equation
     * @throws UnrecognizedEntryException
     */
    public Equation createEquation(String contentSpecification) throws UnrecognizedEntryException{
        contentSpecification=contentSpecification.replace("=","");
        contentSpecification=contentSpecification.replace(")","");
        String [] args = contentSpecification.split("[(]");
        Content [] contents = getArgs(args[1]);
        try {
            switch(args[0]){
                case "ADD":
                    return new ADD(contents[0], contents[1]);
                case "SUB":
                    return new SUB(contents[0], contents[1]);
                case "MUL":
                    return new MUL(contents[0], contents[1]);
                case "DIV":
                    return new DIV(contents[0], contents[1]);
                case "AVERAGE":
                    return new AVERAGE(getGama(contents));
                case "PRODUCT":
                    return new PRODUCT(getGama(contents));
                case "COALESCE":
                    return new COALESCE(getGama(contents));
                case "CONCAT":
                    return new CONCAT(getGama(contents));
                default:
                    throw new UnrecognizedEntryException(args[0]);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw new UnrecognizedEntryException(contentSpecification);
        }
    }

    /**
     * This method is used to get the Gama from an inport  according to the contentSpecification.
     * 
     * @return Gama
     * @throws UnrecognizedEntryException
     */
    public Gama getGama(Content[] cells) throws UnrecognizedEntryException{
        try {
            return new Gama((Cell)((RefCell)cells[0]).getRefCell(),(Cell)((RefCell)cells[1]).getRefCell(), this);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new UnrecognizedEntryException("invalid arguments");
        }
           
    }

    /**
     * This method is used to get the Content from an inport according to the contentSpecification.
     * 
     * @return Content
     * @throws UnrecognizedEntryException
     */
    public Content getContent(String contentSpecification) throws UnrecognizedEntryException{
        try{
            if (contentSpecification.indexOf("'")==0){
                return new ValStr(contentSpecification);
            } else if (contentSpecification.contains("=") && contentSpecification.contains("(")){
                contentSpecification = contentSpecification.replace("=","");
                Equation equation = createEquation(contentSpecification);
                return equation;
            } else if (contentSpecification.contains(";")){
                contentSpecification=contentSpecification.replace("=","");
                Cell refcell = getCellStorage(contentSpecification);
                return new RefCell(refcell);
            } else if (contentSpecification.equals("")){
                return null;
            } else {
                return new ValInt(Integer.parseInt(contentSpecification));
            }
        } catch (NumberFormatException e){
            throw new UnrecognizedEntryException(contentSpecification);
        }
    }


    /**
     * This method is used to get the Content from an Equation's arguments.
     * 
     * @param args
     * @return contents
     * @throws UnrecognizedEntryException
     */
    public Content[] getArgs(String args) throws UnrecognizedEntryException {
        String argSplit[] = args.split("[,]");
        if (argSplit.length == 1)
            argSplit = args.split("[:]");
        Content [] contents = new Content[2];
        contents[0] = getContent(argSplit[0]);
        contents[1] = getContent(argSplit[1]);
        return contents;
    }

    /**
     * This method is used to get a Gama of the cells that have the 
     * specified value associated.
     * 
     * @param contentSpecification
     * @return
     */
    public Gama findValue(String contentSpecification){
        Gama gama = new Gama();
        for (Cell cell : cells.values()){
            try{
                String[] result = cell.getContent().visualize().split("[=]");
                if(result[0].equals(contentSpecification))
                    gama.addCell(cell);
            } catch (NullPointerException e){
                continue;
            }
        }
        return gama;
    }

    public Gama findEvenValues(){
        Gama gama= new Gama();
        for (Cell cell : cells.values()){
            try{
                String[] result = cell.getContent().visualize().split("[=]");
                if(Integer.parseInt(result[0])%2==0)
                    gama.addCell(cell);
            } catch (NullPointerException | ArithmeticException | NumberFormatException e){
                continue;
            }
        }
        return gama;

     }

    /**
     * This method is used to get a Gama of the cells that have the 
     * functions that contain the specified content specification.
     * 
     * @param contentSpecification
     * @return Gama
     * @throws UnrecognizedEntryException
     */
    public Gama findFunction(String contentSpecification){
        Gama gama = new Gama();
        try{
            ArrayList<String> functions= findEquation(contentSpecification);
            for (String function : functions){
                for (Cell cell : cells.values()){
                    try{
                        String[] content = (cell.getContent().visualize()).split("[=]");
                        String[] thisfuncion = content[content.length-1].split("[(]");
                        if(thisfuncion[0].equals(function)){
                            gama.addCell(cell);
                        }
                    } catch (NullPointerException|IndexOutOfBoundsException e){
                        continue;
                    }
                }
            }
        } catch (UnrecognizedEntryException e){  }
        return gama;
    }
    
    /**
     * This method find the list of existent equations that contain the 
     * specified equation prompt.
     * 
     * @param equationPrompt
     * @return
     * @throws UnrecognizedEntryException
     */
    public ArrayList<String> findEquation(String equationPrompt) throws UnrecognizedEntryException{
        String[] functions = {"ADD","AVERAGE", "COALESCE","CONCAT","DIV","PRODUCT", "SUB"};
        ArrayList<String> askedfunctionsList = new ArrayList<String>();
        for(String function: functions){
            if(function.contains(equationPrompt))
                askedfunctionsList.add(function);
        }
        if (askedfunctionsList.size() == 0){
            throw new UnrecognizedEntryException(equationPrompt);
        }
        return askedfunctionsList;
    }

}
