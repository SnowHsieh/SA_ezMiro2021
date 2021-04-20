package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.board2.create.CreateBoard2Input;
import ntut.csie.sslab.kanban.usecase.board2.create.CreateBoard2UseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;

@RestController
public class CreateBoardController {
    private CreateBoard2UseCase createBoard2UseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateBoard2UseCase createBoard2UseCase) {
        this.createBoard2UseCase = createBoard2UseCase;
    }

    @PostMapping(path = "${KANBAN_PREFIX}/teams/{teamId}/boards", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createBoard(@QueryParam("userId") String userId,
                                            @PathVariable("teamId") String teamId,
                                            @RequestBody String boardInfo) {

        String name = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            name = boardJSON.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CreateBoard2Input input = createBoard2UseCase.newInput();
        input.setName(name);
        input.setUserId(userId);
        input.setTeamId(teamId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createBoard2UseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
