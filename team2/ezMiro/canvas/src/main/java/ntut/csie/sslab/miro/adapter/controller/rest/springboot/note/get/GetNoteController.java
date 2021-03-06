package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.get;

import ntut.csie.sslab.miro.adapter.presenter.note.GetNotePresenter;
import ntut.csie.sslab.miro.adapter.presenter.note.NoteViewModel;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteInput;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class GetNoteController {
    private GetNoteUseCase getNoteUseCase;

    @Autowired
    public void setGetNoteUseCase(GetNoteUseCase getNoteUseCase) {
        this.getNoteUseCase = getNoteUseCase;
    }

    @GetMapping(path = "${MIRO_PREFIX}/notes/{noteId}", produces = "application/json")
    public NoteViewModel getNote(@PathVariable("noteId") String noteId,
                                 @QueryParam("boardId") String boardId) {

        GetNoteInput input = (GetNoteInput) getNoteUseCase;
        input.setNoteId(noteId);
        input.setBoardId(boardId);

        GetNotePresenter presenter = new GetNotePresenter();
        getNoteUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}