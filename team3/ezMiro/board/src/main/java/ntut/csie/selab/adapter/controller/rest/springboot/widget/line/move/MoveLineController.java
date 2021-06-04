package ntut.csie.selab.adapter.controller.rest.springboot.widget.line.move;

import ntut.csie.selab.entity.model.widget.Coordinate;
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
                JSONObject coordinateJSON = lineJSON.getJSONObject(key);
                int topLeftX = coordinateJSON.getInt("topLeftX");
                int topLeftY = coordinateJSON.getInt("topLeftY");
                int bottomRightX = coordinateJSON.getInt("bottomRightX");
                int bottomRightY = coordinateJSON.getInt("bottomRightY");

                input.setLineId(key);
                input.setCoordinate(new Coordinate(topLeftX, topLeftY, bottomRightX, bottomRightY));
                moveLineUseCase.execute(input, output);
                lineIds.add(output.getLineId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lineIds;
    }
}
