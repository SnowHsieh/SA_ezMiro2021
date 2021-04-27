package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;

import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.kanban.usecase.figure.ConvertStickerToDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
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
public class GetStickersController {
    private FigureRepository figureRepository;

    @Autowired
    public void setBoardQueryRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @GetMapping(path = "${KANBAN_PREFIX}/board/sticker/getall", produces = "application/json")
    public List<FigureDto> getOnlineUsersInBoard(@QueryParam("boardId") String boardId) {

        List<Figure> stickers = figureRepository.getStickersByBoardId(boardId);
        return ConvertStickerToDto.transform(stickers);
    }
}
