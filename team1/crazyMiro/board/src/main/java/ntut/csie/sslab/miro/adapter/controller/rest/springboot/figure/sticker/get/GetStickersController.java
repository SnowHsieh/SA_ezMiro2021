package ntut.csie.sslab.miro.adapter.controller.rest.springboot.figure.sticker.get;

import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.ConvertStickerToDto;
import ntut.csie.sslab.miro.usecase.figure.FigureDto;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

//package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;
//
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
//import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
//import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardUseCase;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.ws.rs.QueryParam;
//
@RestController
@CrossOrigin
public class GetStickersController {
    private StickerRepository stickerRepository;
    private BoardRepository boardRepository;

    @Autowired
    public void setFigureQueryRepository(StickerRepository stickerRepository) {
        this.stickerRepository = stickerRepository;
    }

    @Autowired
    public void setBoardQueryRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/board/sticker/getall", produces = "application/json")
    public List<FigureDto> getStickers (@QueryParam("boardId") String boardId) {

        List<ConnectionFigure> figures = stickerRepository.getFiguresByBoardId(boardId);
        List<CommittedFigure> committedFigures = boardRepository.findById(boardId).get().getCommittedFigures();

        List<Figure> result = new ArrayList<>();

        committedFigures.forEach(each->{
           result.add(figures.stream().filter(x->x.getFigureId().equals(each.getFigureId())).findFirst().get());
        });

        return ConvertStickerToDto.transform(result);
    }
}
