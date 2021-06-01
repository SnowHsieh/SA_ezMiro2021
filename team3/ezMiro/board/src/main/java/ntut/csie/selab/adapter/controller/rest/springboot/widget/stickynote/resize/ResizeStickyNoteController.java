package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.resize;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteOutput;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class ResizeStickyNoteController {
    ResizeStickyNoteUseCase resizeStickyNoteUseCase;

    @Autowired
    public ResizeStickyNoteController(ResizeStickyNoteUseCase resizeStickyNoteUseCase) {
        this.resizeStickyNoteUseCase = resizeStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}/resize", consumes = "application/json", produces = "application/json")
    public String ResizeStickyNote(@PathVariable("stickyNoteId") String stickyNoteId,
                                   @RequestBody String resizeInfo) {
        ResizeStickyNoteInput input = new ResizeStickyNoteInput();
        ResizeStickyNoteOutput output = new ResizeStickyNoteOutput();
        int topLeftX = 0;
        int topLeftY = 0;
        int bottomLeftX = 0;
        int bottomLeftY = 0;

        try {
            JSONObject resizeJSON = new JSONObject(resizeInfo);
            topLeftX = resizeJSON.getInt("topLeftX");
            topLeftY = resizeJSON.getInt("topLeftY");
            bottomLeftX = resizeJSON.getInt("bottomRightX");
            bottomLeftY = resizeJSON.getInt("bottomRightY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setStickyNoteId(stickyNoteId);
        input.setCoordinate(new Coordinate(topLeftX, topLeftY, bottomLeftX, bottomLeftY));
        resizeStickyNoteUseCase.execute(input, output);

        return output.getStickyNoteId();
    }
}
