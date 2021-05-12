package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.zorder.front;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontInput;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BringNoteToFrontController {
    private BringNoteToFrontUseCase bringNoteToFrontUseCase;

    @Autowired
    public void setBringNoteToFrontUseCase(BringNoteToFrontUseCase bringNoteToFrontUseCase) {
        this.bringNoteToFrontUseCase = bringNoteToFrontUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/zorder/front", produces = "application/json")
    public CqrsCommandViewModel bringNoteToFront(@PathVariable("noteId") String noteId) {
        BringNoteToFrontInput input = bringNoteToFrontUseCase.newInput();
        input.setNoteId(noteId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        bringNoteToFrontUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}