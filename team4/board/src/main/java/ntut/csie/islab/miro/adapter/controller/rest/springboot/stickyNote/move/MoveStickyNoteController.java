package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickyNote.move;

import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.MoveStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.MoveStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class MoveStickyNoteController {
    private MoveStickyNoteUseCase moveStickyNoteUseCase;

    @Autowired
    public void setMoveStickyNoteUseCase(MoveStickyNoteUseCase moveStickyNoteUseCase) {
        this.moveStickyNoteUseCase = moveStickyNoteUseCase;
    }

    @PostMapping(path = "/board/{boardId}/moveStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveStickyNote(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo) {
        UUID figureId = null;
        Position newPosition = null;
        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
            newPosition = new Position(stickyNoteJSON.getDouble("left"),stickyNoteJSON.getDouble("top"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveStickyNoteInput input = moveStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setNewPosition(newPosition);


        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        moveStickyNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }


}
