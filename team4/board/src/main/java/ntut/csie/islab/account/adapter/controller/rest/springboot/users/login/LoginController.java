package ntut.csie.islab.account.adapter.controller.rest.springboot.users.login;

import com.google.gson.Gson;
import ntut.csie.islab.account.users.usecase.login.LoginUseCase;
import ntut.csie.islab.account.users.usecase.login.LoginUseCaseInput;
import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCase;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCaseInput;
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
public class LoginController {
    private LoginUseCase loginUseCase;
    private GetBoardListByUserUseCase getBoardListByUserUseCase;

    @Autowired
    public void setLoginUseCase(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @Autowired
    public void setGetBoardListByUserUseCase(GetBoardListByUserUseCase getBoardListByUserUseCase) {
        this.getBoardListByUserUseCase = getBoardListByUserUseCase;
    }

    @PostMapping(path = "/account/login", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel login(@RequestBody String loginInfo) {
        String username = "";
        String password = "";
        try {
            JSONObject registerJSON = new JSONObject(loginInfo);
            username = registerJSON.getString("username");
            password = registerJSON.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CqrsCommandPresenter output_fianl = CqrsCommandPresenter.newInstance();
        output_fianl.setId(username);

        LoginDto loginDto = new LoginDto();
        loginDto.setLoginMsg("loginMsg");

        LoginUseCaseInput input = loginUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        loginUseCase.execute(input, output);
        String loginMsg = output.buildViewModel().getMessage();

        if(loginMsg == "Login success"){
            GetBoardListByUserUseCaseInput gbinput = getBoardListByUserUseCase.newInput();
            gbinput.setUserName(username);

            CqrsCommandPresenter output2 = CqrsCommandPresenter.newInstance();
            getBoardListByUserUseCase.execute(gbinput,output2);
            String boardListJson = output2.buildViewModel().getMessage();
            loginDto.setLoginMsg(loginMsg);
            loginDto.setBoardListJson(boardListJson);
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(loginDto);
        String jsonStringwithoutdoubleq = jsonString;
        output_fianl.setMessage(jsonStringwithoutdoubleq);

        return output_fianl.buildViewModel();
    }
}
