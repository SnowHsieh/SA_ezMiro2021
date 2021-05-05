package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteInput;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveNoteController {
    private MoveNoteUseCase moveNoteUseCase;

    @Autowired
    public void setMoveNoteUseCase(MoveNoteUseCase moveNoteUseCase) {
        this.moveNoteUseCase = moveNoteUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/move", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveNote(@PathVariable("noteId") String noteId,
                                         @RequestBody String noteInfo) {
        double coordinateX = 0;
        double coordinateY = 0;

        try {
            JSONObject boardJSON = new JSONObject(noteInfo);
            coordinateX = boardJSON.getJSONObject("coordinate").getDouble("x");
            coordinateY = boardJSON.getJSONObject("coordinate").getDouble("y");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveNoteInput input = moveNoteUseCase.newInput();
        input.setNoteId(noteId);
        input.setCoordinate(new Coordinate(coordinateX, coordinateY));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        moveNoteUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}