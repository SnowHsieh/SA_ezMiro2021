package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.bringFigureToFront;//package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.board.bringFigureToFront.BringFigureToFrontInput;
import ntut.csie.sslab.kanban.usecase.board.bringFigureToFront.BringFigureToFrontUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class BringFigureToFrontController {
    private BringFigureToFrontUseCase bringFigureToFrontUseCase;

    @Autowired
    public void setBringFigureToFrontUseCase(BringFigureToFrontUseCase bringFigureToFrontUseCase) {
        this.bringFigureToFrontUseCase = bringFigureToFrontUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/board/{boardId}/figure/bringtofront")
    public CqrsCommandViewModel createBoard(@RequestParam("figureId") String figureId,
                                            @PathVariable("boardId") String boardId) {


        BringFigureToFrontInput input = bringFigureToFrontUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        bringFigureToFrontUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
