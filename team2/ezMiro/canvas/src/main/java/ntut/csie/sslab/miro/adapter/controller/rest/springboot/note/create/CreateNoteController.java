package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class CreateNoteController {
    private CreateNoteUseCase createNoteUseCase;

    @Autowired
    public void setCreateNoteUseCase(CreateNoteUseCase createNoteUseCase) {
        this.createNoteUseCase = createNoteUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/figures/notes", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createNote(@QueryParam("boardId") String boardId,
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

        CreateNoteInput input = createNoteUseCase.newInput();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(coordinateX, coordinateY));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createNoteUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}