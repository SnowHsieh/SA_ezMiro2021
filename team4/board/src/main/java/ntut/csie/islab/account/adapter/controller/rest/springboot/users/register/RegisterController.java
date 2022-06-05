package ntut.csie.islab.account.adapter.controller.rest.springboot.users.register;

import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {
    private RegisterUseCase registerUseCase;

    @Autowired
    public void setRegisterUseCase(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping(path = "/account/register", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel register(@RequestBody String userInfo) {
        String username = "";
        String email = "";
        String password = "";
        try {
            JSONObject registerJSON = new JSONObject(userInfo);
            username = registerJSON.getString("username");
            email = registerJSON.getString("email");
            password = registerJSON.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);
        return output.buildViewModel();
    }
}
