package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.changetype;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeInput;
import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class ChangeCardTypeController {
    private ChangeCardTypeUseCase changeCardTypeUseCase;

    @Autowired
    public void setChangeCardTypeUseCase(ChangeCardTypeUseCase changeCardTypeUseCase) {
        this.changeCardTypeUseCase = changeCardTypeUseCase;
    }

    @PutMapping(path = "${KANBAN_PREFIX}/cards/{cardId}/type", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeCardType(@PathVariable("cardId") String cardId,
                                               @QueryParam("username") String username,
                                               @QueryParam("userId") String userId,
                                               @QueryParam("boardId")String boardId,
                                               @RequestBody String cardInfo) {

        String newType = "";

        try {
            JSONObject cardJSON = new JSONObject(cardInfo);
            newType = cardJSON.getString("newType");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeCardTypeInput input = changeCardTypeUseCase.newInput();
        input.setCardId(cardId);
        input.setNewType(newType);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeCardTypeUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
