package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickyNote.delete;

import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.DeleteStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.DeleteStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class DeleteStickyNoteController {
    private DeleteStickyNoteUseCase deleteStickyNoteUseCase;

    @Autowired
    public void setDeleteStickyNoteUseCase(DeleteStickyNoteUseCase deleteStickyNoteUseCase){
        this.deleteStickyNoteUseCase = deleteStickyNoteUseCase;
    }

    @PostMapping(path = "/board/{boardId}/deleteStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel deleteStickyNote(@PathVariable("boardId") UUID boardId,
                                                 @RequestBody String stickyNoteInfo){
        UUID figureId = null;
        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DeleteStickyNoteInput input = deleteStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        deleteStickyNoteUseCase.execute(input,presenter);
        return presenter.buildViewModel();

    }
}
