package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.changecolor;

import ntut.csie.islab.miro.usecase.figure.line.changecolor.ChangeLineColorInput;
import ntut.csie.islab.miro.usecase.figure.line.changecolor.ChangeLineColorUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class ChangeLineColorController {
    private ChangeLineColorUseCase changeLineColorUseCase;

    @Autowired
    public void setChangeLineColorUseCase(ChangeLineColorUseCase changeLineColorUseCase){
        this.changeLineColorUseCase = changeLineColorUseCase;
    }
    @PostMapping(path = "/board/{boardId}/changeLineColor", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeLineColor(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String lineInfo){
        UUID figureId = null;
        String color = "";

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
            color = lineJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ChangeLineColorInput input = changeLineColorUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setColor(color);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeLineColorUseCase.execute(input, presenter);
        return presenter.buildViewModel();


    }




}
