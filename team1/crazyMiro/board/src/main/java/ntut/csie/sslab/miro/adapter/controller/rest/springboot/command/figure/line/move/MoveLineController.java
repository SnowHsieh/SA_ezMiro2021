package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.line.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineInput;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MoveLineController {
    private MoveLineUseCase moveLineUseCase;

    @Autowired
    public void setMoveStickerUseCase(MoveLineUseCase moveLineUseCase) {
        this.moveLineUseCase = moveLineUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/line/move", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveLine(@RequestParam("lineId") String lineId,
                                                     @RequestBody String lineInfo) {

        Coordinate sourcePosition = null;
        Coordinate targetPosition = null;
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            Long x = lineJSON.getJSONObject("sourcePosition").getLong("x");
            Long y = lineJSON.getJSONObject("sourcePosition").getLong("y");
            sourcePosition = new Coordinate(x, y);

            x = lineJSON.getJSONObject("targetPosition").getLong("x");
            y = lineJSON.getJSONObject("targetPosition").getLong("y");
            targetPosition = new Coordinate(x, y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveLineInput input = moveLineUseCase.newInput();
        input.setLineId(lineId);
        input.setSourcePosition(sourcePosition);
        input.setTargetPosition(targetPosition);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        moveLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

