package ntut.csie.selab.adapter.controller.rest.springboot.widget.movewidget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteInput;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteOutput;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteUseCase;
import org.json.JSONArray;
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
    public List<String> moveStickyNote(@RequestBody String stickyNoteInfo) {
        MoveStickyNoteInput input = new MoveStickyNoteInput();
        MoveStickyNoteOutput output = new MoveStickyNoteOutput();
        List<String> stickyNoteIds = new ArrayList<>();

        try {
            JSONObject stickNoteJSON = new JSONObject(stickyNoteInfo);
            Iterator iterator = stickNoteJSON.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject coordinateJSON = stickNoteJSON.getJSONObject(key);
                int topLeftX = coordinateJSON.getInt("topLeftX");
                int topLeftY = coordinateJSON.getInt("topLeftY");
                int bottomRightX = coordinateJSON.getInt("bottomRightX");
                int bottomRightY = coordinateJSON.getInt("bottomRightY");

                input.setStickyNoteId(key);
                input.setCoordinate(new Coordinate(topLeftX, topLeftY, bottomRightX, bottomRightY));
                moveStickyNoteUseCase.execute(input, output);
                stickyNoteIds.add(output.getStickyNoteId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stickyNoteIds;
    }
}
