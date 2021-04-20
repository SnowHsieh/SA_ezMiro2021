package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.changedescription;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionInput;
import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;

@RestController
public class ChangeCardDescriptionController {
    private ChangeCardDescriptionUseCase changeCardDescriptionUseCase;

    @Autowired
    public void setChangeCardDescriptionUseCase(ChangeCardDescriptionUseCase changeCardDescriptionUseCase) {
        this.changeCardDescriptionUseCase = changeCardDescriptionUseCase;
    }

    @PutMapping(path = "${KANBAN_PREFIX}/cards/{cardId}/description", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeCardDescription(@PathVariable("cardId") String cardId,
                                                      @QueryParam("userId")String userId,
                                                      @QueryParam("username")String username,
                                                      @QueryParam("boardId") String boardId,
                                                      @RequestBody String cardInfo) {

        String description = "";

        try {
            JSONObject cardJSON = new JSONObject(cardInfo);
            description = cardJSON.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeCardDescriptionInput input = changeCardDescriptionUseCase.newInput();
        input.setCardId(cardId);
        input.setNewDescription(description);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeCardDescriptionUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }

}
