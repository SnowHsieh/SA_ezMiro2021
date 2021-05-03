package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickyNote.edit;

import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.EditStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.EditStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class EditStickyNoteController {
    private EditStickyNoteUseCase editStickyNoteUseCase;

    @Autowired
    public void setEditStickyNoteUseCase(EditStickyNoteUseCase editStickyNoteUseCase) {
        this.editStickyNoteUseCase = editStickyNoteUseCase;
    }

    @PostMapping(path = "/board/{boardId}/editStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel editStickyNote(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo) {
        UUID figureId = null;
        String content = "";

        Style style = null;
        JSONObject styleJsonObject;
        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
            content = stickyNoteJSON.getString("content");
            styleJsonObject = stickyNoteJSON.getJSONObject("style");
            System.out.println("styleJsonObject" + styleJsonObject);
            style = new Style(styleJsonObject.getInt("fontSize"),
                    ShapeKindEnum.values()[styleJsonObject.getInt("shape")],
                    styleJsonObject.getDouble("width"),
                    styleJsonObject.getDouble("height"),
                    styleJsonObject.getString("color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditStickyNoteInput input = editStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setContent(content);
        input.setStyle(style);


        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        editStickyNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }


}
