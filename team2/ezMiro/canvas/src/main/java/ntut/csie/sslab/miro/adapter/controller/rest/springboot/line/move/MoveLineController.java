package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineInput;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveLineController {
    private MoveLineUseCase moveLineUseCase;

    @Autowired
    public void setMoveLineUseCase(MoveLineUseCase moveLineUseCase) {
        this.moveLineUseCase = moveLineUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/lines/{lineId}/move", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveLine(@PathVariable("lineId") String lineId,
                                         @RequestBody String lineInfo) {
        double deltaX = 0;
        double deltaY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            deltaX = lineJSON.getJSONObject("delta").getDouble("x");
            deltaY = lineJSON.getJSONObject("delta").getDouble("y");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveLineInput input = moveLineUseCase.newInput();
        input.setLineId(lineId);
        input.setDelta(new Coordinate(deltaX, deltaY));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        moveLineUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}