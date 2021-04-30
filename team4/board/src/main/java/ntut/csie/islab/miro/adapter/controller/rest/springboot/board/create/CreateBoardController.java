package ntut.csie.islab.miro.adapter.controller.rest.springboot.board.create;


import ntut.csie.islab.miro.usecase.board.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class CreateBoardController {
    private CreateBoardUseCase createBoardUseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateBoardUseCase createBoardUseCase) {
        this.createBoardUseCase = createBoardUseCase;
    }

    @PostMapping(path = "/team/{teamId}/boards", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createBoard(
            @PathVariable("teamId") UUID teamId,
            @RequestBody String boardInfo) {
        String boardName = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            boardName = boardJSON.getString("boardName");
            System.out.println("post: "+boardName);
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