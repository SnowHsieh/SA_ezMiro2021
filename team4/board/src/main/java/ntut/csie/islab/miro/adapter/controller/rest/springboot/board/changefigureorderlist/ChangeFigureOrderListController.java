package ntut.csie.islab.miro.adapter.controller.rest.springboot.board.changefigureorderlist;

import ntut.csie.islab.miro.usecase.board.ChangeFigureOrderListOnBoardInput;
import ntut.csie.islab.miro.usecase.board.ChangeFigureOrderListOnBoardUseCase;
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
@CrossOrigin(value = "http://localhost:8080")
public class ChangeFigureOrderListController {
    private ChangeFigureOrderListOnBoardUseCase changeFigureOrderListOnBoardUseCase ;

    @Autowired
    public void setChangeFigureOrderListOnBoardUseCase(ChangeFigureOrderListOnBoardUseCase changeFigureOrderListOnBoardUseCase){
        this.changeFigureOrderListOnBoardUseCase = changeFigureOrderListOnBoardUseCase;
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
        ChangeFigureOrderListOnBoardInput input = changeFigureOrderListOnBoardUseCase.newInput();
        input.setBoardId(boardId);
        input.setCommittedFigureListOrder(figureOrderList);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeFigureOrderListOnBoardUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }



}
