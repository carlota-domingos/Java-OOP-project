package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;    
import xxl.Calculator;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;


/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSave extends Command<Calculator> {

    DoSave(Calculator receiver) {
        super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            try{
                _receiver.save();
            }catch (MissingFileAssociationException e){
                String filename = Form.requestString(Prompt.newSaveAs());
                _receiver.saveAs(filename);
            }
        } catch (UnavailableFileException e){
            throw new FileOpenFailedException(e);
        } 

    }
}
