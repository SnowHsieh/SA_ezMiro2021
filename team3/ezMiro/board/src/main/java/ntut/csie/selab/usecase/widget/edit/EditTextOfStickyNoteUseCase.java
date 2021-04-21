package ntut.csie.selab.usecase.widget.edit;

import ntut.csie.selab.entity.model.StickyNote;
import ntut.csie.selab.entity.model.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class EditTextOfStickyNoteUseCase {

    private WidgetRepository widgetRepository;

    public EditTextOfStickyNoteUseCase(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    public void execute(EditTextOfStickyNoteInput input, EditTextOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());
        if(stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.setText(input.getText());
            output.setModifiedText(selectedStickyNote.getText());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
