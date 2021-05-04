package ntut.csie.team5.adapter.controller.rest.springboot.board.change_order;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.entity.model.board.OrderType;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureOrderInput;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureOrderUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class ChangeFigureOrderController {

    private ChangeFigureOrderUseCase changeFigureOrderUseCase;

    @Autowired
    public void setChangeFigureOrderUseCase(ChangeFigureOrderUseCase changeFigureOrderUseCase) {
        this.changeFigureOrderUseCase = changeFigureOrderUseCase;
    }

    @PostMapping(path = "/bring-figure-front", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel bringFigureToFront(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureOrderInput input = changeFigureOrderUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setOrderType(OrderType.FRONT);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/bring-figure-front-end", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel bringFigureToFrontEnd(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureOrderInput input = changeFigureOrderUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setOrderType(OrderType.FRONT_END);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/send-figure-back", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel sendFigureToBack(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureOrderInput input = changeFigureOrderUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setOrderType(OrderType.BACK);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/send-figure-back-end", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel sendFigureToBackEnd(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureOrderInput input = changeFigureOrderUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setOrderType(OrderType.BACK_END);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
