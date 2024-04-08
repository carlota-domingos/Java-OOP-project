package xxl.app.edit;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.UnrecognizedEntryException;


/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

    DoShow(Spreadsheet receiver) {
        super(Label.SHOW, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        String interval = Form.requestString(Prompt.address());
        try {
            _display.popup(_receiver.Show(interval)); 
        } catch (UnrecognizedEntryException e) {
            throw new InvalidCellRangeException(interval);
        }
    }

}
