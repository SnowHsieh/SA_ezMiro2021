package ntut.csie.selab.adapter.controller.rest.springboot.widget.line;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.usecase.widget.line.create.CreateLineInput;
import ntut.csie.selab.usecase.widget.line.create.CreateLineOutput;
import ntut.csie.selab.usecase.widget.line.create.CreateLineUseCase;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteOutput;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class CreateLineController {
    private CreateLineUseCase createLineUseCase;

    @Autowired
    public CreateLineController(CreateLineUseCase createLineUseCase) {
        this.createLineUseCase = createLineUseCase;
    }

    @PostMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/lines", consumes = "application/json", produces = "application/json")
    public String createLine(@PathVariable("boardId") String boardId,
                             @RequestBody String lineInfo) {
        CreateLineInput input = new CreateLineInput();
        CreateLineOutput output = new CreateLineOutput();

        int topLeftX = 0;
        int topLeftY = 0;
        int bottomRightX = 0;
        int bottomRightY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            topLeftX = lineJSON.getInt("topLeftX");
            topLeftY = lineJSON.getInt("topLeftY");
            bottomRightX = lineJSON.getInt("bottomRightX");
            bottomRightY = lineJSON.getInt("bottomRightY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(topLeftX, topLeftY, bottomRightX, bottomRightY));
        createLineUseCase.execute(input, output);

        return output.getLineId();
    }
}
