package xxl.gama;

import xxl.content.Content;

/**
 * This class represents a cell.
 */
public class Cell extends Gama {
    private int line;
    /**the line of this cell */
    private int column;
    /**the column of this cell */
    private Content content= null;
    /**the content of this cell */

    public Cell(int line, int column, Content content) {
        this.line = line;
        this.column = column;
        this.content = content;
    }

    public Cell (int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getline() {
        return line;
    }

    public int getcolumn() {
        return column;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * Clears the cell's content.
     */
    public void clear() {
        this.content = null;
    }
    /**
     * Returns the visualization of the cell's content.
     * 
     * @return String
     */
    public String viewContent() {
        try{
            return content.visualize();
        } catch (NullPointerException e){
            return "";
        }
    }

    /**
     * Returns the visualization of the cell.
     * 
     * @return String
     */
    @Override
    public String visualize() {
        if (content == null) {
            return this.toString() + "|";
        } else
            return  this.toString()+"|" + viewContent();
    }

    @Override
    public String toString() {
        return line + ";" + column;
    }
}
