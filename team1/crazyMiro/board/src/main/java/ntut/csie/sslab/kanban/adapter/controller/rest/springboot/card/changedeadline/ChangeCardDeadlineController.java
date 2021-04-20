package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.changedeadline;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineInput;
import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class ChangeCardDeadlineController {
    private ChangeCardDeadlineUseCase changeCardDeadlineUseCase;

    @Autowired
    public void setChangeCardDeadlineUseCase(ChangeCardDeadlineUseCase changeCardDeadlineUseCase) {
        this.changeCardDeadlineUseCase = changeCardDeadlineUseCase;
    }

    @PutMapping(path = "${KANBAN_PREFIX}/cards/{cardId}/deadline", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeCardDeadline(@PathVariable("cardId") String cardId,
                                                   @QueryParam("username") String username,
                                                   @QueryParam("userId") String userId,
                                                   @QueryParam("boardId")String boardId,
                                                   @RequestBody String cardInfo) {

        String newDeadline = "";


        try {
            JSONObject cardJSON = new JSONObject(cardInfo);
            newDeadline = cardJSON.getString("newDeadline");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeCardDeadlineInput input = changeCardDeadlineUseCase.newInput();
        input.setCardId(cardId);
        input.setNewDeadline(newDeadline);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeCardDeadlineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
