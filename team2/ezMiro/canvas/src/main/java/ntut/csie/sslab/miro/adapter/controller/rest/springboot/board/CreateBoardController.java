package ntut.csie.sslab.miro.adapter.controller.rest.springboot.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.board.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.CreateBoardUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class CreateBoardController {
    private CreateBoardUseCase createBoardUseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateBoardUseCase createBoardUseCase) {
        this.createBoardUseCase = createBoardUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/boards", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createBoard(@QueryParam("teamId") String teamId,
                                            @RequestBody String boardInfo) {
        String boardName = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardName = boardJSON.getString("boardName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateBoardInput input = createBoardUseCase.newInput();
        input.setTeamId(teamId);
        input.setBoardName(boardName);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createBoardUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}