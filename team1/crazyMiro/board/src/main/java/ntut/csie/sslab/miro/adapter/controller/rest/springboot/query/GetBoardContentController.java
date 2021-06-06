package ntut.csie.sslab.miro.adapter.controller.rest.springboot.query;

import ntut.csie.sslab.miro.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.ConvertStickerToDto;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;
import ntut.csie.sslab.miro.usecase.figure.line.ConvertLineToDto;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
public class GetBoardContentController {
    private StickerRepository stickerRepository;
    private LineRepository lineRepository;
    private BoardRepository boardRepository;

    @Autowired
    public void setStickerQueryRepository(StickerRepository stickerRepository) {
        this.stickerRepository = stickerRepository;
    }

    @Autowired
    public void setLineQueryRepository(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Autowired
    public void setBoardQueryRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/board/getcontent", produces = "application/json")
    public BoardContentViewModel getBoardContent (@RequestParam("boardId") String boardId) {

        List<ConnectionFigure> figures = stickerRepository.getFiguresByBoardId(boardId);
        List<CommittedFigure> committedFigures = boardRepository.findById(boardId).get().getCommittedFigures();

        List<Figure> stickers = new ArrayList<>();

        committedFigures.forEach(each->{
            stickers.add(figures.stream().filter(x->x.getFigureId().equals(each.getFigureId())).findFirst().get());
        });

        List<Line> lines = lineRepository.getLineByBoardId(boardId);

        BoardContentViewModel result = new BoardContentViewModel();
        result.setBoardId(boardId);
        result.setLines(ConvertLineToDto.transform(lines));
        result.setStickers(ConvertStickerToDto.transform(stickers));

        return result;
    }
}
