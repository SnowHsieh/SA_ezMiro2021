package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.edit_text;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditNoteTextController {

    private EditNoteTextUseCase editNoteTextUseCase;

    @Autowired
    public void setEditNoteTextUseCase(EditNoteTextUseCase editNoteTextUseCase) {
        this.editNoteTextUseCase = editNoteTextUseCase;
    }

    @PostMapping(path = "/edit-note-text", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel editNoteText(@RequestBody String noteInfo) {
        String figureId = "";
        String text = "";
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            figureId = noteJSON.getString("figureId");
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
