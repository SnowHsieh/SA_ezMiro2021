package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteInput;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class DeleteNoteController {
    private DeleteNoteUseCase deleteNoteUseCase;

    @Autowired
    public void setDeleteNoteUseCase(DeleteNoteUseCase deleteNoteUseCase) {
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/delete", produces = "application/json")
    public CqrsCommandViewModel deleteNote(@PathVariable("noteId") String noteId,
                                           @QueryParam("boardId") String boardId) {
        DeleteNoteInput input = deleteNoteUseCase.newInput();
        input.setNoteId(noteId);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        deleteNoteUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}