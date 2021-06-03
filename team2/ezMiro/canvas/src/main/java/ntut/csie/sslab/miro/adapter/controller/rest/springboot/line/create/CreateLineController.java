package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class CreateLineController {
    private CreateLineUseCase createLineUseCase;

    @Autowired
    public void setCreateLineUseCase(CreateLineUseCase createLineUseCase) {
        this.createLineUseCase = createLineUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/figures/lines", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createLine(@QueryParam("boardId") String boardId,
                                           @RequestBody String lineInfo) {
        String startConnectableFigureId = "";
        String endConnectableFigureId = "";
        double startOffsetX = 0;
        double startOffsetY = 0;
        double endOffsetX = 0;
        double endOffsetY = 0;

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            startConnectableFigureId = lineJSON.getString("startConnectableFigureId");
            endConnectableFigureId = lineJSON.getString("endConnectableFigureId");
            startOffsetX = lineJSON.getJSONObject("startOffset").getDouble("x");
            startOffsetY = lineJSON.getJSONObject("startOffset").getDouble("y");
            endOffsetX = lineJSON.getJSONObject("endOffset").getDouble("x");
            endOffsetY = lineJSON.getJSONObject("endOffset").getDouble("y");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateLineInput input = createLineUseCase.newInput();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId(startConnectableFigureId);
        input.setEndConnectableFigureId(endConnectableFigureId);
        input.setStartOffset(new Coordinate(startOffsetX, startOffsetY));
        input.setEndOffset(new Coordinate(endOffsetX, endOffsetY));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createLineUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}