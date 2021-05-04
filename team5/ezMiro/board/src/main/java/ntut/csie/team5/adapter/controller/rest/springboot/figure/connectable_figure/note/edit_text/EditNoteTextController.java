package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.edit_text;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.QueryParam;
import java.awt.*;

public class EditNoteTextController {

    private EditNoteTextUseCase editNoteTextUseCase;

    @Autowired
    public void setEditNoteTextUseCase(EditNoteTextUseCase editNoteTextUseCase) {
        this.editNoteTextUseCase = editNoteTextUseCase;
    }

    @PostMapping(path = "/edit-note-text", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel editNoteText(@QueryParam("figureId") String figureId, @RequestBody String noteInfo) {
        String text = "";
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            text = noteJSON.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditNoteTextInput input = editNoteTextUseCase.newInput();
        input.setFigureId(figureId);
        input.setText(text);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        editNoteTextUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
