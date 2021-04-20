package ntut.csie.sslab.account.users.command.adapter.restcontroller;

import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterInput;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class RegisterController {
    private RegisterUseCase registerUseCase;

    @Autowired
    private void setRegisterUserUseCase(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping(path = "${ACCOUNT_PREFIX}/users/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CqrsCommandViewModel> registerUser(@RequestBody String userInfo){

        String username = "";
        String password = "";
        String email= "";
        String nickname = "";
        String role = "";
        boolean isThirdParty = false;

        try {
            JSONObject userJSON = new JSONObject(userInfo);

            username = userJSON.getString("username");
            password = userJSON.getString("password");
            email = userJSON.getString("email");
            nickname = userJSON.getString("nickname");
            role = userJSON.getString("role");
            isThirdParty = userJSON.getBoolean("isThirdParty");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(isThirdParty);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, presenter);

        if(presenter.getExitCode() == ExitCode.SUCCESS) {
            return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.OK);
        }

        return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
