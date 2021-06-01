package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.editTextOfStickyNote;

import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteUseCase;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class EditTextOfStickyNoteController {
    private EditTextOfStickyNoteUseCase editTextOfStickyNoteUseCase;

    @Autowired
    public EditTextOfStickyNoteController(EditTextOfStickyNoteUseCase editTextOfStickyNoteUseCase) {
        this.editTextOfStickyNoteUseCase = editTextOfStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}/text", consumes = "application/json", produces = "application/json")
    public String editTextOfSticky(@PathVariable("boardId") String boardId,
                                   @PathVariable("stickyNoteId") String stickyNoteId,
                                   @RequestBody String widgetInfo) {
        EditTextOfStickyNoteInput input = new EditTextOfStickyNoteInput();
        EditTextOfStickyNoteOutput output = new EditTextOfStickyNoteOutput();

        String newText = "";
        try {
            JSONObject widgetJSON = new JSONObject(widgetInfo);
            newText = widgetJSON.getString("newText");
        } catch (Exception e) {
            e.printStackTrace();
        }
        input.setStickyNoteId(stickyNoteId);
        input.setText(newText);
        editTextOfStickyNoteUseCase.execute(input, output);
        return output.getStickyNoteId();
    }
}

