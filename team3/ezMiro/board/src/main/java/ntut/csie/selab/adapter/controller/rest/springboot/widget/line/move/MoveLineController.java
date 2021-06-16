package ntut.csie.selab.adapter.controller.rest.springboot.widget.line.move;

import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.usecase.widget.line.move.MoveLineInput;
import ntut.csie.selab.usecase.widget.line.move.MoveLineOutput;
import ntut.csie.selab.usecase.widget.line.move.MoveLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class MoveLineController {
    private MoveLineUseCase moveLineUseCase;

    @Autowired
    public MoveLineController(MoveLineUseCase moveLineUseCase) {
        this.moveLineUseCase = moveLineUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/lines/move", consumes = "application/json", produces = "application/json")
    public List<String> moveLine(@PathVariable("boardId") String boardId, @RequestBody String lineInfo) {
        MoveLineInput input = new MoveLineInput();
        MoveLineOutput output = new MoveLineOutput();
        List<String> lineIds = new ArrayList<>();
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            Iterator iterator = lineJSON.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject positionJSON = lineJSON.getJSONObject(key);
                int topLeftX = positionJSON.getInt("topLeftX");
                int topLeftY = positionJSON.getInt("topLeftY");
                int bottomRightX = positionJSON.getInt("bottomRightX");
                int bottomRightY = positionJSON.getInt("bottomRightY");

                input.setLineId(key);
                input.setPosition(new Position(topLeftX, topLeftY, bottomRightX, bottomRightY));
                moveLineUseCase.execute(input, output);
                lineIds.add(output.getLineId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lineIds;
    }
}
