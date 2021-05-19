package ntut.csie.team5.adapter.controller.rest.springboot.board.change_order;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.entity.model.board.ZOrderType;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderInput;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class ChangeFigureZOrderController {

    private ChangeFigureZOrderUseCase changeFigureZOrderUseCase;

    @Autowired
    public void setChangeFigureZOrderUseCase(ChangeFigureZOrderUseCase changeFigureZOrderUseCase) {
        this.changeFigureZOrderUseCase = changeFigureZOrderUseCase;
    }

    @PostMapping(path = "/bring-figure-forward", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel bringFigureForward(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureZOrderInput input = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setZOrderType(ZOrderType.BRING_FORWARD);

        changeFigureZOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/bring-figure-to-front", consumes = "application/json", produces = "application/json")
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

        ChangeFigureZOrderInput input = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setZOrderType(ZOrderType.BRING_TO_FRONT);

        changeFigureZOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/send-figure-backwards", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel sendFigureBackwards(@RequestBody String boardInfo) {
        String boardId = "";
        String figureId = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardId = boardJSON.getString("boardId");
            figureId = boardJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeFigureZOrderInput input = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setZOrderType(ZOrderType.SEND_BACKWARDS);

        changeFigureZOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

    @PostMapping(path = "/send-figure-to-back", consumes = "application/json", produces = "application/json")
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

        ChangeFigureZOrderInput input = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setZOrderType(ZOrderType.SEND_TO_BACK);

        changeFigureZOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
