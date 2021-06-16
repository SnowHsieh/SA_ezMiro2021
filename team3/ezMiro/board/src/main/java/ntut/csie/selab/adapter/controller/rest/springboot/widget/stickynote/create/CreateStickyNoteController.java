package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.create;


import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class CreateStickyNoteController {
    private CreateStickyNoteUseCase createStickyNoteUseCase;

    @Autowired
    public CreateStickyNoteController (CreateStickyNoteUseCase createStickyNoteUseCase) {
        this.createStickyNoteUseCase = createStickyNoteUseCase;

    }

    @PostMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/", consumes = "application/json", produces = "application/json")
    public String createStickyNote(@PathVariable("boardId") String boardId,
                                   @RequestBody String widgetInfo) {
        CreateStickyNoteInput input = new CreateStickyNoteInput();
        CreateStickyNoteOutput output = new CreateStickyNoteOutput();

        int topLeftX = 0;
        int topLeftY = 0;
        int bottomRightX = 0;
        int bottomRightY = 0;

        try {
            JSONObject widgetJSON = new JSONObject(widgetInfo);
            topLeftX = widgetJSON.getInt("topLeftX");
            topLeftY = widgetJSON.getInt("topLeftY");
            bottomRightX = widgetJSON.getInt("bottomRightX");
            bottomRightY = widgetJSON.getInt("bottomRightY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setBoardId(boardId);
        input.setPosition(new Position(topLeftX, topLeftY, bottomRightX, bottomRightY));
        createStickyNoteUseCase.execute(input, output);

        return output.getStickyNoteId();
    }
}
