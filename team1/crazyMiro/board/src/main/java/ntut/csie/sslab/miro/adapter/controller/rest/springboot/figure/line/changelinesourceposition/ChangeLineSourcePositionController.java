package ntut.csie.sslab.miro.adapter.controller.rest.springboot.figure.line.changelinesourceposition;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ChangeLineSourcePositionController {
    private ChangeSourcePositionUseCase changeSourcePositionUseCase;

    @Autowired
    public void setChangeLineSourcePositionUseCase(ChangeSourcePositionUseCase changeSourcePositionUseCase) {
        this.changeSourcePositionUseCase = changeSourcePositionUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/line/changesourceposition", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeSourcePosition(@RequestParam("lineId") String lineId,
                                                     @RequestBody String lineInfo) {

        Coordinate sourcePosition = null;
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            Long x = lineJSON.getJSONObject("sourcePosition").getLong("x");
            Long y = lineJSON.getJSONObject("sourcePosition").getLong("y");
            sourcePosition = new Coordinate(x, y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeSourcePositionInput input = changeSourcePositionUseCase.newInput();
        input.setFigureId(lineId);
        input.setSourcePosition(sourcePosition);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeSourcePositionUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

