package ntut.csie.selab.adapter.controller.rest.springboot.widget;

import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.edit.text.EditTextOfStickyNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class EditZIndexOfStickyNoteController {
    private EditZIndexOfStickyNoteUseCase editZIndexOfStickyNoteUseCase;

    @Autowired
    public EditZIndexOfStickyNoteController(EditZIndexOfStickyNoteUseCase editZIndexOfStickyNoteUseCase) {
        this.editZIndexOfStickyNoteUseCase = editZIndexOfStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}/z-index", consumes = "application/json", produces = "application/json")
    public String EditZIndexOfStickyNote(@PathVariable("boardId") String boardId,
                                         @PathVariable("stickyNoteId") String stickyNoteId,
                                         @RequestBody String zIndexInfo) {
        EditZIndexOfStickyNoteInput input = new EditZIndexOfStickyNoteInput();
        EditZIndexOfStickyNoteOutput output = new EditZIndexOfStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        int zIndex = 0;
        try {
            JSONObject zIndexJson = new JSONObject(zIndexInfo);
            zIndex = zIndexJson.getInt("zIndex");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        input.setStickyNoteId(stickyNoteId);
        input.setzIndex(zIndex);
        editZIndexOfStickyNoteUseCase.execute(input, output);

        return output.getStickyNoteId();
    }
}
