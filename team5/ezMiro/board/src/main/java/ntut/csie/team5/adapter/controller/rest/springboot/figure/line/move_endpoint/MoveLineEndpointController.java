package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.move_endpoint;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.line.move_endpoint.MoveLineEndpointInput;
import ntut.csie.team5.usecase.figure.line.move_endpoint.MoveLineEndpointUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class MoveLineEndpointController {

    private MoveLineEndpointUseCase moveLineEndpointUseCase;

    @Autowired      
    public void setMoveLineEndpointUseCase(MoveLineEndpointUseCase moveLineEndpointUseCase) {
        this.moveLineEndpointUseCase = moveLineEndpointUseCase;
    }

    @PostMapping(path = "/move-line-endpoint", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveLineEndpoint(@RequestBody String lineInfo) {
        String figureId = "";
        String endpointId = "";
        int positionX = 0;
        int positionY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = lineJSON.getString("figureId");
            endpointId = lineJSON.getString("endpointId");
            positionX = lineJSON.getInt("positionX");
            positionY = lineJSON.getInt("positionY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveLineEndpointInput input = moveLineEndpointUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setFigureId(figureId);
        input.setEndpointId(endpointId);
        input.setPositionX(positionX);
        input.setPositionY(positionY);

        moveLineEndpointUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
