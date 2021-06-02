package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.connecttofigure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureInput;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectLineToFigureController {
    private ConnectLineToFigureUseCase connectLineToFigureUseCase;

    @Autowired
    public void setConnectLineToFigureUseCase(ConnectLineToFigureUseCase connectLineToFigureUseCase) {
        this.connectLineToFigureUseCase = connectLineToFigureUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/lines/{lineId}/connect-to-figure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel connectLineToFigure(@PathVariable("lineId") String lineId,
                                                    @RequestBody String lineInfo) {
        String figureId = "";
        String linePoint = "";
        double offsetX = 0;
        double offsetY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            offsetX = lineJSON.getJSONObject("offset").getDouble("x");
            offsetY = lineJSON.getJSONObject("offset").getDouble("y");
            linePoint = lineJSON.getString("linePoint");
            figureId = lineJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectLineToFigureInput input = connectLineToFigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setLinePoint(LinePoint.valueOf(linePoint));
        input.setFigureId(figureId);
        input.setOffset(new Coordinate(offsetX, offsetY));
        connectLineToFigureUseCase.execute(input, output);

        return output.buildViewModel();
    }
}