package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.color;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorInput;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeNoteColorController {
    private ChangeNoteColorUseCase changeNoteColorUseCase;

    @Autowired
    public void setChangeNoteColorUseCase(ChangeNoteColorUseCase changeNoteColorUseCase) {
        this.changeNoteColorUseCase = changeNoteColorUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/color", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeNoteColor(@PathVariable("noteId") String noteId,
                                                @RequestBody String noteInfo) {
        String color = "#FFF9B1";

        try {
            JSONObject boardJSON = new JSONObject(noteInfo);
            color = boardJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        input.setNoteId(noteId);
        input.setColor(color);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeNoteColorUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}