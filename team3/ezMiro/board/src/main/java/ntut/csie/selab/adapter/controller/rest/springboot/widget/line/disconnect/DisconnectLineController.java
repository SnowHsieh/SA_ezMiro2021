package ntut.csie.selab.adapter.controller.rest.springboot.widget.line.disconnect;

import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineInput;
import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineOutput;
import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class DisconnectLineController {
    private DisconnectLineUseCase disconnectLineUseCase;

    @Autowired
    public DisconnectLineController(DisconnectLineUseCase disconnectLineUseCase) {
        this.disconnectLineUseCase = disconnectLineUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/lines/disconnect", consumes = "application/json", produces = "application/json")
    public String disconnectLine(@PathVariable("boardId") String boardId,
                             @RequestBody String lineInfo) {
        DisconnectLineInput input = new DisconnectLineInput();
        DisconnectLineOutput output = new DisconnectLineOutput();

        String lineId = "";
        String endPoint = "";

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            lineId = lineJSON.getString("lineId");
            endPoint = lineJSON.getString("endPoint");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setBoardId(boardId);
        input.setLineId(lineId);
        input.setEndPoint(endPoint);

        disconnectLineUseCase.execute(input, output);

        return output.getLineId();
    }
}
