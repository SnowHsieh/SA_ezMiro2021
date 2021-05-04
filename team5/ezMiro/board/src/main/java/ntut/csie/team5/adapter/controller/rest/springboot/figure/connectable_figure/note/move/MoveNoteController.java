package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.QueryParam;
import java.awt.*;

public class MoveNoteController {

    private MoveNoteUseCase moveNoteUseCase;

    @Autowired
    public void setMoveNoteUseCase(MoveNoteUseCase moveNoteUseCase) {
        this.moveNoteUseCase = moveNoteUseCase;
    }

    @PostMapping(path = "/move-note", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveNote(@QueryParam("figureId") String figureId, @RequestBody String noteInfo) {
        int leftTopPositionX = 0;
        int leftTopPositionY = 0;
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            leftTopPositionX = noteJSON.getInt("leftTopPositionX");
            leftTopPositionY = noteJSON.getInt("leftTopPositionY");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveNoteInput input = moveNoteUseCase.newInput();
        input.setFigureId(figureId);
        input.setLeftTopPosition(new Point(leftTopPositionX, leftTopPositionY));

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        moveNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
