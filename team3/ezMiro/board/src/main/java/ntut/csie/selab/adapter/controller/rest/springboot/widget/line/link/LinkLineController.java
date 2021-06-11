package ntut.csie.selab.adapter.controller.rest.springboot.widget.line.link;

import ntut.csie.selab.usecase.widget.line.link.LinkLineInput;
import ntut.csie.selab.usecase.widget.line.link.LinkLineOutput;
import ntut.csie.selab.usecase.widget.line.link.LinkLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class LinkLineController {
    private LinkLineUseCase linkLineUseCase;

    @Autowired
    public LinkLineController(LinkLineUseCase linkLineUseCase) {
        this.linkLineUseCase = linkLineUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/lines/link", consumes = "application/json", produces = "application/json")
    public String linkLine(@PathVariable("boardId") String boardId, @RequestBody String lineInfo) {
        LinkLineInput input = new LinkLineInput();
        LinkLineOutput output = new LinkLineOutput();

        String lineId = "";
        String endPoint = "";
        String targetId = "";

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            lineId = lineJSON.getString("widgetId");
            endPoint = lineJSON.getString("endPoint");
            targetId = lineJSON.getString("targetId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setBoardId(boardId);
        input.setLineId(lineId);
        input.setEndPoint(endPoint);
        input.setTargetId(targetId);

        linkLineUseCase.execute(input, output);

        return output.getLineId();
    }
}
