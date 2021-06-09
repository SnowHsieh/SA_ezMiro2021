package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.draw;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineInput;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class DrawLineController {

    private DrawLineUseCase drawLineUseCase;

    @Autowired
    public void setDrawLineUseCase(DrawLineUseCase drawLineUseCase) {
        this.drawLineUseCase = drawLineUseCase;
    }

    @PostMapping(path = "/draw-line", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel drawLine(@RequestBody String lineInfo) {
        String boardId = "";
        Endpoint endpointA = null;
        Endpoint endpointB = null;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            boardId = lineJSON.getString("boardId");
            JSONObject endpointAJson = lineJSON.getJSONObject("endpointA");
            JSONObject endpointBJson = lineJSON.getJSONObject("endpointB");

            endpointA = new Endpoint(endpointAJson.getString("id"), endpointAJson.getInt("positionX"), endpointAJson.getInt("positionY"), endpointAJson.getString("connectedFigureId"));
            endpointB = new Endpoint(endpointBJson.getString("id"), endpointBJson.getInt("positionX"), endpointBJson.getInt("positionY"), endpointBJson.getString("connectedFigureId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DrawLineInput input = drawLineUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setEndpointA(endpointA);
        input.setEndpointB(endpointB);

        drawLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
