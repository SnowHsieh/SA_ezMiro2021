package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.QueryParam;

public class DeleteNoteController {

    private DeleteNoteUseCase deleteNoteUseCase;

    @Autowired
    public void setDeleteNoteUseCase(DeleteNoteUseCase deleteNoteUseCase) {
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @PostMapping(path = "/delete-note", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel deleteNote(@QueryParam("boardId") String boardId, @QueryParam("figureId") String figureId) {

        DeleteNoteInput input = deleteNoteUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        deleteNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
