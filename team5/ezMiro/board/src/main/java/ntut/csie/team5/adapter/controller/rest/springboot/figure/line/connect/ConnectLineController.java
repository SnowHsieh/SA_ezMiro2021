package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.connect;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.line.connect.ConnectLineInput;
import ntut.csie.team5.usecase.figure.line.connect.ConnectLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class ConnectLineController {

    private ConnectLineUseCase connectLineUseCase;

    @Autowired
    public void setConnectLineUseCase(ConnectLineUseCase connectLineUseCase) {
        this.connectLineUseCase = connectLineUseCase;
    }

    @PostMapping(path = "/connect-line", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel connectLine(@RequestBody String lineInfo) {
        String figureId = "";
        String endpointId = "";
        String connectFigureId = "";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = lineJSON.getString("figureId");
            endpointId = lineJSON.getString("endpointId");
            connectFigureId = lineJSON.getString("connectFigureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectLineInput connectLineInput = connectLineUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        connectLineInput.setFigureId(figureId);
        connectLineInput.setEndpointId(endpointId);
        connectLineInput.setConnectedFigureId(connectFigureId);

        connectLineUseCase.execute(connectLineInput, presenter);
        return presenter.buildViewModel();
    }
}
