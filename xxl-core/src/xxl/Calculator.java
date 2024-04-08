package xxl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;

import java.util.List;
import java.util.ArrayList;

import xxl.exceptions.ImportFileException;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {

    /** The name of the file associated to the current spreadsheet. */
    private String _filename;
    /** The current spreadsheet. */
    private Spreadsheet _spreadsheet = null;
    /** The list of users. */
    private List<User> _users = new ArrayList<User>();
    /** The current user. */
    private User _currentUser = new User("root");
    

    public Calculator(){
        _users.add(_currentUser);
    }

    /**
     * @return the current changed status.
     */
    public boolean changesMade(){
        return _spreadsheet.changesMade();
    }

    /**
     * @return the name of the file associated to the current network.
     */
    public String getFileName(){
        return _filename;
    }

    /**
     * @return the current spreadsheet.
     */
    public Spreadsheet getSpreadsheet(){
       return _spreadsheet;
    }

    /**
     * @return the current user.
     */
    public User getUser(){
        return _currentUser;
    }

    /**
     * Associates the current network to the specified spreadsheet.
     * 
     * @param spreadsheet the spreadsheet to be associated to the current network.
     */
    public void setSpreadsheet(Spreadsheet spreadsheet){
        _spreadsheet = spreadsheet;
        _currentUser.addSpreadsheet(_spreadsheet);
    }

    /**
     * Associates the current network to the specified file.
     * 
     * @param filename
     */
    public void setFilename(String filename){
        _filename = filename;
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws MissingFileAssociationException , UnavailableFileException{
        if(_filename == null){
            throw new MissingFileAssociationException();
        }
        saveAs(_filename);  
    }
  
    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws UnavailableFileException{
        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename +".dat")))){
            _spreadsheet.setChanged(false);
            out.writeObject(_spreadsheet);
            _filename = filename;
        } catch (IOException e){
            throw new UnavailableFileException(filename);
        }

    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename+".dat")))){
            _spreadsheet = (Spreadsheet) in.readObject();
            _filename = filename;
        } catch (IOException|ClassNotFoundException e){
            throw new UnavailableFileException(filename);
        } 
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String lines= in.readLine();
            int nLines= Integer.parseInt(lines.replace("linhas=", ""));
            String columns= in.readLine();
            int nColumns= Integer.parseInt(columns.replace("colunas=", ""));
            _spreadsheet = new Spreadsheet(nLines, nColumns, _currentUser);
            _currentUser.addSpreadsheet(_spreadsheet);
            String keys;
            while ((keys= in.readLine())!= null) {
                String[] args= keys.split("[|]");
                if (args.length == 1) {
                    args= new String[] {args[0], ""};
                }
                _spreadsheet.insertContents(args[0], args[1]);
            }
        } catch (IOException|UnrecognizedEntryException|NumberFormatException  e) {
            throw new ImportFileException(filename, e);
        }
    }

}
