package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.displayorder;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.displayorder.ChangeNoteDisplayOrderInput;
import ntut.csie.sslab.miro.usecase.note.edit.displayorder.ChangeNoteDisplayOrderUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeNoteDisplayOrderController {
    private ChangeNoteDisplayOrderUseCase changeNoteDisplayOrderUseCase;

    @Autowired
    public void setChangeNoteDisplayOrderUseCase(ChangeNoteDisplayOrderUseCase changeNoteDisplayOrderUseCase) {
        this.changeNoteDisplayOrderUseCase = changeNoteDisplayOrderUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/displayorder", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeNoteDisplayOrder(@PathVariable("noteId") String noteId,
                                                @RequestBody String noteInfo) {
        int displayOrder = 0;

        try {
            JSONObject boardJSON = new JSONObject(noteInfo);
            displayOrder = boardJSON.getInt("displayOrder");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeNoteDisplayOrderInput input = changeNoteDisplayOrderUseCase.newInput();
        input.setNoteId(noteId);
        input.setDisplayOrder(displayOrder);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeNoteDisplayOrderUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}