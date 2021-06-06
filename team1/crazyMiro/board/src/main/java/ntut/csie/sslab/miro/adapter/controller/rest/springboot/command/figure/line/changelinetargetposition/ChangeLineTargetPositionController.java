package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.line.changelinetargetposition;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ChangeLineTargetPositionController {
    private ChangeTargetPositionUseCase changeTargetPositionUseCase;

    @Autowired
    public void setChangeLineTargetPositionUseCase(ChangeTargetPositionUseCase changeTargetPositionUseCase) {
        this.changeTargetPositionUseCase = changeTargetPositionUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/line/changetargetposition", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeTargetPosition(@RequestParam("lineId") String lineId,
                                                     @RequestBody String lineInfo) {

        Coordinate targetPosition = null;
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            Long x = lineJSON.getJSONObject("targetPosition").getLong("x");
            Long y = lineJSON.getJSONObject("targetPosition").getLong("y");
            targetPosition = new Coordinate(x, y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeTargetPositionInput input = changeTargetPositionUseCase.newInput();
        input.setFigureId(lineId);
        input.setTargetPosition(targetPosition);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeTargetPositionUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

