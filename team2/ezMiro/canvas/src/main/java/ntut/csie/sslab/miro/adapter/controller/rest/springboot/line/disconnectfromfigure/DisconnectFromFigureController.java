package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.disconnectfromfigure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureInput;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisconnectFromFigureController {
    private DisconnectLineFromFigureUseCase disconnectLineFromFigureUseCase;

    @Autowired
    public void setDisconnectLineFromFigureUseCase(DisconnectLineFromFigureUseCase disconnectLineFromFigureUseCase) {
        this.disconnectLineFromFigureUseCase = disconnectLineFromFigureUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/lines/{lineId}/disconnect-from-figure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel disconnectLineFromFigure(@PathVariable("lineId") String lineId,
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

        DisconnectLineFromFigureInput input = disconnectLineFromFigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setLinePoint(LinePoint.valueOf(linePoint));
        input.setFigureId(figureId);
        input.setPointOffset(new Coordinate(offsetX, offsetY));
        disconnectLineFromFigureUseCase.execute(input, output);

        return output.buildViewModel();
    }
}