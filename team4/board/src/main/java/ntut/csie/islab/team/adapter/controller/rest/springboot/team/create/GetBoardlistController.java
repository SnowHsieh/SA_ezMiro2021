package ntut.csie.islab.team.adapter.controller.rest.springboot.team.create;

import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCase;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCaseInput;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCase;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCaseInput;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class GetBoardlistController {
    private GetBoardListByUserUseCase getBoardListByUserUseCase;

    @Autowired
    public void setGetBoardListByUserUseCase(GetBoardListByUserUseCase getBoardListByUserUseCase) {
        this.getBoardListByUserUseCase = getBoardListByUserUseCase;
    }

    @PostMapping(path = "user/{userName}/getBoardList", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createTeam(@PathVariable("userName") String userName, @RequestBody String teamInfo) {

        GetBoardListByUserUseCaseInput input = getBoardListByUserUseCase.newInput();
        input.setUserName(userName);
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        getBoardListByUserUseCase.execute(input, output);
        return output.buildViewModel();
    }
}
