package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickyNote.create;


import ntut.csie.islab.miro.figure.entity.model.figure.Position;
import ntut.csie.islab.miro.figure.entity.model.figure.ShapeKindEnum;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CreateStickyNoteController {
    private CreateStickyNoteUseCase createStickyNoteUseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateStickyNoteUseCase createStickyNoteUseCase) {
        this.createStickyNoteUseCase = createStickyNoteUseCase;
    }

    //todo
//    @PostMapping(path = "/board/{boardId}/createStikcyNote", consumes = "application/json", produces = "application/json")
//    public CqrsCommandViewModel createBoard(
//            @PathVariable("boardId") UUID boardId,
//            @RequestBody String stickyNoteInfo) {
    @PostMapping(path = "/board/{boardId}/createStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createBoard(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo) {

        String content = "";
        Position position = null;
        Style style = null;
        JSONObject styleJsonObject;
        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            content = stickyNoteJSON.getString("content");
            position = new Position(stickyNoteJSON.getJSONObject("position").getDouble("x"), stickyNoteJSON.getJSONObject("position").getDouble("y"));
            styleJsonObject = stickyNoteJSON.getJSONObject("style");
            style = new Style(styleJsonObject.getInt("fontSize"),
                    ShapeKindEnum.CIRCLE,//todo
                    styleJsonObject.getDouble("figureSize"),
                    styleJsonObject.getString("color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
//        input.setBoardId(UUID.fromString("b0a0d2d2-625c-4c83-a537-fe822a9ff135"));
        input.setPosition(position);
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createStickyNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

}