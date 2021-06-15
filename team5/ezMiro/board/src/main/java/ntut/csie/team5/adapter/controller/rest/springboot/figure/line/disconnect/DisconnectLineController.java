package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.disconnect;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.line.disconnect.DisconnectLineInput;
import ntut.csie.team5.usecase.figure.line.disconnect.DisconnectLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class DisconnectLineController {

    private DisconnectLineUseCase disconnectLineUseCase;

    @Autowired
    public void setDisconnectLineUseCase(DisconnectLineUseCase disconnectLineUseCase) {
        this.disconnectLineUseCase = disconnectLineUseCase;
    }

    @PostMapping(path = "/disconnect-line", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel connectLine(@RequestBody String lineInfo) {
        String figureId = "";
        String endpointId = "";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = lineJSON.getString("figureId");
            endpointId = lineJSON.getString("endpointId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DisconnectLineInput disconnectLineInput = disconnectLineUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        disconnectLineInput.setFigureId(figureId);
        disconnectLineInput.setEndpointId(endpointId);

        disconnectLineUseCase.execute(disconnectLineInput, presenter);
        return presenter.buildViewModel();
    }
}
