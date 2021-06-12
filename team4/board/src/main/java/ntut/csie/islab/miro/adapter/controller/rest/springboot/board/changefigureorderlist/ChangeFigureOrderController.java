package ntut.csie.islab.miro.adapter.controller.rest.springboot.board.changefigureorderlist;

import ntut.csie.islab.miro.usecase.board.changefigureorder.ChangeFigureOrderInput;
import ntut.csie.islab.miro.usecase.board.changefigureorder.ChangeFigureOrderUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ChangeFigureOrderController {
    private ChangeFigureOrderUseCase changeFigureOrderUseCase;

    @Autowired
    public void setChangeFigureOrderUseCase(ChangeFigureOrderUseCase changeFigureOrderUseCase){
        this.changeFigureOrderUseCase = changeFigureOrderUseCase;
    }
    @PostMapping(path = "/boards/{boardId}/changeFigureOrder", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeFigureOrder(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String figureOrderListInfo ){
        List<UUID> figureOrderList = new ArrayList<>();
        try {
            JSONObject figureOrderListInfoJSON = new JSONObject(figureOrderListInfo);
            JSONArray jsonArray = figureOrderListInfoJSON.getJSONArray("figureOrderList");

            for (int i=0; i<jsonArray.length(); i++) {
                figureOrderList.add(UUID.fromString(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ChangeFigureOrderInput input = changeFigureOrderUseCase.newInput();
        input.setBoardId(boardId);
        input.setCommittedFigureListOrder(figureOrderList);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }



}
