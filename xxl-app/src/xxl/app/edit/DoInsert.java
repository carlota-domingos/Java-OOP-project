package xxl.app.edit;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

    DoInsert(Spreadsheet receiver) {
        super(Label.INSERT, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        String range = Form.requestString(Prompt.address());
        String content = Form.requestString(Prompt.content());
        try{
            _receiver.insertContents(range, content);
        } catch (UnrecognizedEntryException e){
            throw new InvalidCellRangeException(range);
        }

        
    }

}
