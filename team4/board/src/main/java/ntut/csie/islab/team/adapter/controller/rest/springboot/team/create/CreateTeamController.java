package ntut.csie.islab.team.adapter.controller.rest.springboot.team.create;

import ntut.csie.islab.account.users.usecase.login.LoginUseCase;
import ntut.csie.islab.account.users.usecase.login.LoginUseCaseInput;
import ntut.csie.islab.team.usecase.CreateTeamUseCase;
import ntut.csie.islab.team.usecase.CreateTeamUseCaseInput;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CreateTeamController {
    private CreateTeamUseCase createTeamUseCase;

    @Autowired
    public void setCreateTeamUseCase(CreateTeamUseCase createTeamUseCase) {
        this.createTeamUseCase = createTeamUseCase;
    }

    @PostMapping(path = "/team/createTeam", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createTeam(@RequestBody String teamInfo) {
        String username = "";
        String teamName = "";
        try {
            JSONObject registerJSON = new JSONObject(teamInfo);
            username = registerJSON.getString("username");
            teamName = registerJSON.getString("teamName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateTeamUseCaseInput input = createTeamUseCase.newInput();
        input.setAdmin(username);
        input.setTeamName(teamName);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        createTeamUseCase.execute(input, output);
        return output.buildViewModel();
    }
}
