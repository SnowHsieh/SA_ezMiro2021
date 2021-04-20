package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.changeestimate;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateInput;
import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class ChangeCardEstimateController {
    private ChangeCardEstimateUseCase changeCardEstimateUseCase;

    @Autowired
    public void setChangeCardEstimateUseCase(ChangeCardEstimateUseCase changeCardEstimateUseCase) {
        this.changeCardEstimateUseCase = changeCardEstimateUseCase;
    }

    @PutMapping(path = "${KANBAN_PREFIX}/cards/{cardId}/estimate", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeCardEstimate(@PathVariable("cardId") String cardId,
                                                   @QueryParam("userId") String userId,
                                                   @QueryParam("username") String username,
                                                   @QueryParam("boardId") String boardId,
                                                   @RequestBody String cardInfo) {

        String estimate = "";

        try {
            JSONObject cardJSON = new JSONObject(cardInfo);
            estimate = cardJSON.getString("estimate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeCardEstimateInput input = changeCardEstimateUseCase.newInput();
        input.setCardId(cardId);
        input.setNewEstimate(estimate);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeCardEstimateUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

}
