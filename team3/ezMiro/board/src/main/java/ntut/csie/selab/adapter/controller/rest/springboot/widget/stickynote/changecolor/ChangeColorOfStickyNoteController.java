package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.changecolor;

import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class ChangeColorOfStickyNoteController {
    ChangeColorOfStickyNoteUseCase changeColorOfStickyNoteUseCase;

    @Autowired
    public ChangeColorOfStickyNoteController(ChangeColorOfStickyNoteUseCase changeColorOfStickyNoteUseCase) {
        this.changeColorOfStickyNoteUseCase = changeColorOfStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}/color", consumes = "application/json", produces = "application/json")
    public String changeColorOfStickyNote(@PathVariable("stickyNoteId") String stickyNoteId,
                                          @RequestBody String colorInfo) {
        ChangeColorOfStickyNoteInput input = new ChangeColorOfStickyNoteInput();
        ChangeColorOfStickyNoteOutput output = new ChangeColorOfStickyNoteOutput();
        String color = "";

        try {
            JSONObject colorJSON = new JSONObject(colorInfo);
            color = colorJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setStickyNoteId(stickyNoteId);
        input.setStickyNoteColor(color);
        changeColorOfStickyNoteUseCase.execute(input, output);

        return output.getStickyNoteId();
    }
}
