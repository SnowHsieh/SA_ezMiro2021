package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointInput;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveLinePointController {
    private MoveLinePointUseCase moveLinePointUseCase;

    @Autowired
    public void setMoveLinePointUseCase(MoveLinePointUseCase moveLinePointUseCase) {
        this.moveLinePointUseCase = moveLinePointUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/lines/{lineId}/move-point", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveLine(@PathVariable("lineId") String lineId,
                                         @RequestBody String lineInfo) {
        double pointDeltaX = 0;
        double pointDeltaY = 0;
        String linePoint = "";

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            pointDeltaX = lineJSON.getJSONObject("pointDelta").getDouble("x");
            pointDeltaY = lineJSON.getJSONObject("pointDelta").getDouble("y");
            linePoint = lineJSON.getString("linePoint");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveLinePointInput input = moveLinePointUseCase.newInput();
        input.setLineId(lineId);
        input.setPointDelta(new Coordinate(pointDeltaX, pointDeltaY));
        input.setLinePoint(LinePoint.valueOf(linePoint));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        moveLinePointUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}