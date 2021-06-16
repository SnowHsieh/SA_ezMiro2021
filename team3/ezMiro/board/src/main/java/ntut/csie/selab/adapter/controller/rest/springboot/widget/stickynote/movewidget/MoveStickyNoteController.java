package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.movewidget;

import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class MoveStickyNoteController {
    private MoveStickyNoteUseCase moveStickyNoteUseCase;

    @Autowired
    public MoveStickyNoteController(MoveStickyNoteUseCase moveStickyNoteUseCase) {
        this.moveStickyNoteUseCase = moveStickyNoteUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/move", consumes = "application/json", produces = "application/json")
    public List<String> moveStickyNote(@PathVariable("boardId") String boardId, @RequestBody String stickyNoteInfo) {
        MoveStickyNoteInput input = new MoveStickyNoteInput();
        MoveStickyNoteOutput output = new MoveStickyNoteOutput();
        List<String> stickyNoteIds = new ArrayList<>();

        try {
            JSONObject stickNoteJSON = new JSONObject(stickyNoteInfo);
            Iterator iterator = stickNoteJSON.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject positionJSON = stickNoteJSON.getJSONObject(key);
                int topLeftX = positionJSON.getInt("topLeftX");
                int topLeftY = positionJSON.getInt("topLeftY");
                int bottomRightX = positionJSON.getInt("bottomRightX");
                int bottomRightY = positionJSON.getInt("bottomRightY");

                input.setStickyNoteId(key);
                input.setPosition(new Position(topLeftX, topLeftY, bottomRightX, bottomRightY));
                moveStickyNoteUseCase.execute(input, output);
                stickyNoteIds.add(output.getStickyNoteId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stickyNoteIds;
    }
}
