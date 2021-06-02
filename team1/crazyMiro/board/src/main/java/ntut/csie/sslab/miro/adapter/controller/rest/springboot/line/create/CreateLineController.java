package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CreateLineController {
    private CreateLineUseCase createLineUseCase;

    @Autowired
    public void setCreateLineUseCase(CreateLineUseCase createLineUseCase) {
        this.createLineUseCase = createLineUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/board/line/create", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createLine(@RequestParam("boardId") String boardId,
                                           @RequestBody String lineInfo) {
        String sourceId = "";
        String targetId = "";
        Coordinate sourcePosition = null;
        Coordinate targetPosition = null;
        try {
            JSONObject lineJSON = new JSONObject((lineInfo));
            sourceId = lineJSON.getString("sourceId");
            targetId = lineJSON.getString("targetId");
            sourcePosition = new Coordinate(lineJSON.getLong("sourceX"), lineJSON.getLong("sourceY"));
            targetPosition = new Coordinate(lineJSON.getLong("targetX"), lineJSON.getLong("targetY"));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        CreateLineInput input = createLineUseCase.newInput();
        input.setBoardId(boardId);
        input.setSourceId(sourceId);
        input.setTargetId(targetId);
        input.setSourcePosition(sourcePosition);
        input.setTargetPosition(targetPosition);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
