package ntut.csie.islab.account.adapter.controller.rest.springboot.users.login;

import ntut.csie.islab.account.users.usecase.login.LoginUseCase;
import ntut.csie.islab.account.users.usecase.login.LoginUseCaseInput;
import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
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

    @Autowired
    public void setLoginUseCase(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
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

        LoginUseCaseInput input = loginUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        loginUseCase.execute(input, output);
        return output.buildViewModel();
    }
}
