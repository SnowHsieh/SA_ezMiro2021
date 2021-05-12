package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.zorder.back;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackInput;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendNoteToBackController {
    private SendNoteToBackUseCase sendNoteToBackUseCase;

    @Autowired
    public void setSendNoteToBackUseCase(SendNoteToBackUseCase sendNoteToBackUseCase) {
        this.sendNoteToBackUseCase = sendNoteToBackUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/zorder/back", produces = "application/json")
    public CqrsCommandViewModel sendNoteToBack(@PathVariable("noteId") String noteId) {
        SendNoteToBackInput input = sendNoteToBackUseCase.newInput();
        input.setNoteId(noteId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        sendNoteToBackUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}