package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.textfigure.stickynote.create;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.create.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.create.CreateStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@CrossOrigin
public class CreateStickyNoteController {
    private CreateStickyNoteUseCase createStickyNoteUseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateStickyNoteUseCase createStickyNoteUseCase) {
        this.createStickyNoteUseCase = createStickyNoteUseCase;
    }


    @PostMapping(path = "/board/{boardId}/createStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createStickyNote(
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
                    ShapeKindEnum.values()[styleJsonObject.getInt("shape")],
                    styleJsonObject.getDouble("width"),
                    styleJsonObject.getDouble("height"),
                    styleJsonObject.getString("color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
        input.setPosition(position);
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createStickyNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

}