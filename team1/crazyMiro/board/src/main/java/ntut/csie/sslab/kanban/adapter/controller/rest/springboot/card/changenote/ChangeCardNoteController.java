package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.changenote;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteUseCase;
import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteInput;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class ChangeCardNoteController {
    private ChangeCardNoteUseCase changeCardNoteUseCase;

    @Autowired
    public void setChangeCardNotesUseCase(ChangeCardNoteUseCase changeCardNoteUseCase) {
        this.changeCardNoteUseCase = changeCardNoteUseCase;
    }

    @PutMapping(path = "${KANBAN_PREFIX}/cards/{cardId}/note", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeCardNote(@PathVariable("cardId") String cardId,
                                               @QueryParam("userId") String userId,
                                               @QueryParam("username") String username,
                                               @QueryParam("boardId") String boardId,
                                               @RequestBody String cardInfo) {

        String newNotes = "";

        try {
            JSONObject cardJSON = new JSONObject(cardInfo);
            newNotes = cardJSON.getString("newNotes");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeCardNoteInput input = changeCardNoteUseCase.newInput();
        input.setCardId(cardId);
        input.setNewNotes(newNotes);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);


        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeCardNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
