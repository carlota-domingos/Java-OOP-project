package xxl;

import java.util.ArrayList;

/**
 * This class represents a user.
 */
public class User implements java.io.Serializable {
    private String name;
    private ArrayList<Spreadsheet> spreadsheets;
    
    User(String name){
        this.name = name;
        spreadsheets = new ArrayList<Spreadsheet>();
    }

    /**
     * Adds a spreadsheet to this user.
     */
    public void addSpreadsheet(Spreadsheet spreadsheet){
        spreadsheets.add(spreadsheet);
    }
    
}
