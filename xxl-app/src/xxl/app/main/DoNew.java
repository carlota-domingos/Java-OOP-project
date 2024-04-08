package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;
import xxl.Spreadsheet;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;


/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

    DoNew(Calculator receiver) {
        super(Label.NEW, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        String filename=null;
        if(_receiver.getSpreadsheet() != null && _receiver.changesMade()) {
            boolean answer = Form.confirm(Prompt.saveBeforeExit());
            try{
                if(answer) {
                    try{
                        _receiver.save();
                    }catch (MissingFileAssociationException e){
                        filename = Form.requestString(Prompt.newSaveAs());
                        _receiver.saveAs(filename);
                    }
                }
            }catch (UnavailableFileException e){
                throw new FileOpenFailedException(e);
            }
        }
        _receiver.setFilename(null);
        _receiver.setSpreadsheet(new Spreadsheet(Form.requestInteger(Prompt.lines()), Form.requestInteger(Prompt.columns()), _receiver.getUser()));
        
        
    
    }

}
