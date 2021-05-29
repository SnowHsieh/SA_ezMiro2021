package ntut.csie.islab.miro.adapter.controller.rest.springboot.line.create;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textfigure.Style;
import ntut.csie.islab.miro.usecase.figure.line.create.CreateLineInput;
import ntut.csie.islab.miro.usecase.figure.line.create.CreateLineUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.create.CreateStickyNoteInput;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class CreateLineController {
    private CreateLineUseCase createLineUseCase;
    @Autowired
    public void setCreateLineUseCase(CreateLineUseCase createLineUseCase){
        this.createLineUseCase = createLineUseCase;
    }
    @PostMapping(path = "/board/{boardId}/createLine", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createLine(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String lineInfo) {


        List<Position> positionList = new ArrayList<Position>();

        int strokeWidth = 0;
        String color = "";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            JSONArray positionJsonArray = lineJSON.getJSONArray("positionList");
            for (int i=0; i<positionJsonArray.length(); i++) {
                JSONObject positionItem = positionJsonArray.getJSONObject(i);
                positionList.add(new Position(positionItem.getDouble("x"),positionItem.getDouble("y")));
            }
            strokeWidth = lineJSON.getInt("strokeWidth");
            color = lineJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateLineInput input = createLineUseCase.newInput();

        input.setBoardId(boardId);
        input.setPositionList(positionList);
        input.setStrokeWidth(strokeWidth);
        input.setColor(color);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();

    }
}
