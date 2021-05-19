package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.resize;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class ResizeNoteController {

    private ResizeNoteUseCase resizeNoteUseCase;

    @Autowired
    public void setResizeNoteUseCase(ResizeNoteUseCase resizeNoteUseCase) {
        this.resizeNoteUseCase = resizeNoteUseCase;
    }

    @PostMapping(path = "/resize-note", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel resizeNote(@RequestBody String noteInfo) {
        String figureId = "";
        int height = 0;
        int width = 0;
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            figureId = noteJSON.getString("figureId");
            height = noteJSON.getInt("height");
            width = noteJSON.getInt("width");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResizeNoteInput input = resizeNoteUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setFigureId(figureId);
        input.setHeight(height);
        input.setWidth(width);

        resizeNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
