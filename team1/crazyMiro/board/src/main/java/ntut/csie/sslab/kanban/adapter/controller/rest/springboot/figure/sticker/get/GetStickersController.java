package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sticker.get;

import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.kanban.usecase.figure.ConvertStickerToDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import org.json.JSONException;
import org.json.JSONObject;
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
    private FigureRepository figureRepository;
    private BoardRepository boardRepository;

    @Autowired
    public void setFigureQueryRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Autowired
    public void setBoardQueryRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/board/sticker/getall", produces = "application/json")
    public List<FigureDto> getStickers (@QueryParam("boardId") String boardId) {

        List<Figure> figures = figureRepository.getFiguresByBoardId(boardId);
        List<CommittedFigure> committedFigures = boardRepository.findById(boardId).get().getCommittedFigures();

        List<Figure> figures1 = new ArrayList<>();

        committedFigures.forEach(each->{
           figures1.add(figures.stream().filter(x->x.getFigureId().equals(each.getFigureId())).findFirst().get());
        });

        return ConvertStickerToDto.transform(figures1);
    }
}
