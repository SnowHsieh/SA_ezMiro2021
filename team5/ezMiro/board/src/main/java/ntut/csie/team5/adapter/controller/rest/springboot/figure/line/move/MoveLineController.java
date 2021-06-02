package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.line.move.MoveLineInput;
import ntut.csie.team5.usecase.figure.line.move.MoveLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class MoveLineController {

    private MoveLineUseCase moveLineUseCase;

    @Autowired
    public void setMoveLineUseCase(MoveLineUseCase moveLineUseCase) {
        this.moveLineUseCase = moveLineUseCase;
    }

    @PostMapping(path = "/move-line", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveLine(@RequestBody String lineInfo) {
        String figureId = "";
        int offsetX = 0;
        int offsetY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = lineJSON.getString("figureId");
            offsetX = lineJSON.getInt("offsetX");
            offsetY = lineJSON.getInt("offsetY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveLineInput input = moveLineUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setFigureId(figureId);
        input.setOffsetX(offsetX);
        input.setOffsetY(offsetY);

        moveLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
