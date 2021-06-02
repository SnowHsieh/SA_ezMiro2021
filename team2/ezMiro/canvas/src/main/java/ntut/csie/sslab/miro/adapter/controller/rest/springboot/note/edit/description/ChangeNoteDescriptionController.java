package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.description;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeNoteDescriptionController {
    private ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase;

    @Autowired
    public void setChangeNoteDescriptionUseCase(ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase) {
        this.changeNoteDescriptionUseCase = changeNoteDescriptionUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/description", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeNoteDescription(@PathVariable("noteId") String noteId,
                                                @RequestBody String noteInfo) {
        String description = "";

        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            description = noteJSON.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeNoteDescriptionInput input = changeNoteDescriptionUseCase.newInput();
        input.setNoteId(noteId);
        input.setDescription(description);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeNoteDescriptionUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}