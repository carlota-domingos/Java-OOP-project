package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;



/**
 * Open existing file.
 */
class DoOpen extends Command<Calculator> {

    DoOpen(Calculator receiver) {
        super(Label.OPEN, receiver);
        
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
        filename= Form.requestString(Prompt.openFile());
        try {
            _receiver.load(filename);
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        } 
    }

}
 