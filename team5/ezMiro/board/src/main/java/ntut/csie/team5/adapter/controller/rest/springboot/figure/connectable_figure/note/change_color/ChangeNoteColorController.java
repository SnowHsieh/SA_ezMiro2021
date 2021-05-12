package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.change_color;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")

@RestController
public class ChangeNoteColorController {

    private ChangeNoteColorUseCase changeNoteColorUseCase;

    @Autowired
    public void setChangeNoteColorUseCase(ChangeNoteColorUseCase changeNoteColorUseCase) {
        this.changeNoteColorUseCase = changeNoteColorUseCase;
    }

    @PostMapping(path = "/change-note-color", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeNoteColor(@RequestBody String noteInfo) {
        String figureId = "";
        String color = "#000000";
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            figureId = noteJSON.getString("figureId");
            color = noteJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        input.setFigureId(figureId);
        input.setColor(color);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeNoteColorUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
