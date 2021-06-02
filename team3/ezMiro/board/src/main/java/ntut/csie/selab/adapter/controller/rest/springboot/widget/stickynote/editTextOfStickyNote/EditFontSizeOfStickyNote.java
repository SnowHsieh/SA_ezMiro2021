package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.editTextOfStickyNote;

import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteUseCase;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class EditFontSizeOfStickyNote {
    private EditFontSizeOfStickyNoteUseCase editFontSizeOfStickyNoteUseCase;

    @Autowired
    public EditFontSizeOfStickyNote(EditFontSizeOfStickyNoteUseCase editFontSizeOfStickyNoteUseCase) {
        this.editFontSizeOfStickyNoteUseCase = editFontSizeOfStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}/font-size", consumes = "application/json", produces = "application/json")
    public String editFontSizeOfStickyNote(@PathVariable("boardId") String boardId,
                                           @PathVariable("stickyNoteId") String stickyNoteId,
                                           @RequestBody String fontInfo) {
        EditFontSizeOfStickyNoteInput input = new EditFontSizeOfStickyNoteInput();
        EditFontSizeOfStickyNoteOutput output = new EditFontSizeOfStickyNoteOutput();

        int fontSize = 0;
        try {
            JSONObject fontInfoJSON = new JSONObject(fontInfo);
            fontSize = fontInfoJSON.getInt("fontSize");
        } catch (Exception e) {
            e.printStackTrace();
        }

        input.setStickyNoteId(stickyNoteId);
        input.setFontSize(fontSize);

        editFontSizeOfStickyNoteUseCase.execute(input, output);

        return output.getStickyNoteId();
    }
}
