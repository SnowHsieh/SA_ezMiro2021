package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.get;

import ntut.csie.sslab.miro.adapter.presenter.note.GetNotePresenter;
import ntut.csie.sslab.miro.adapter.presenter.note.NoteViewModel;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteInput;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class GetAllNoteController {
    private GetNoteUseCase getNoteUseCase;
    private BoardRepository boardRepository;

    @Autowired
    public void setGetNoteUseCase(GetNoteUseCase getNoteUseCase) {
        this.getNoteUseCase = getNoteUseCase;
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/notes", produces = "application/json")
    public Board getNotes(@QueryParam("boardId") String boardId) {

        Board board = boardRepository.findById(boardId).get();
        return board;
//        GetNoteInput input = (GetNoteInput) getNoteUseCase;
//        input.setBoardId(boardId);
//
//        GetNotePresenter presenter = new GetNotePresenter();
//        getNoteUseCase.execute(input, presenter);

//        return presenter.buildViewModel();
    }
}