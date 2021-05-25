package ntut.csie.sslab.miro.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.sslab.miro.adapter.presenter.board.BoardViewModel;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.note.ConvertNoteToDTO;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GetBoardContentController {
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/boards/{boardId}/getcontent", produces = "application/json")
    public BoardViewModel getContent(@PathVariable("boardId") String boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<NoteDTO> result = ConvertNoteToDTO.transform(figureRepository.findByBoardId(boardId).stream().map(f -> (Note)f).collect(Collectors.toList()));
        Map<String, CommittedFigure> committedFigureMap = board.getCommittedFigures();

        for (NoteDTO noteDto : result) {
            noteDto.setZOrder(committedFigureMap.get(noteDto.getNoteId()).getZOrder());
        }

        BoardViewModel boardViewModel = new BoardViewModel();
        boardViewModel.setBoardId(boardId);
        boardViewModel.setBoardChannel(board.getBoardChannel());
        boardViewModel.setNotes(result);
        return boardViewModel;
    }
}