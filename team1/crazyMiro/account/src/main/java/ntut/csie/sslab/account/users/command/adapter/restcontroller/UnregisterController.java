package ntut.csie.sslab.account.users.command.adapter.restcontroller;

import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterInput;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnregisterController {
    private UnregisterUseCase unregisterUseCase;

    @Autowired
    private void setUnregisterUseCase(UnregisterUseCase unregisterUseCase) {
        this.unregisterUseCase = unregisterUseCase;
    }

    @PutMapping(path = "${ACCOUNT_PREFIX}/users/{userId}/unregister", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CqrsCommandViewModel> unregisterUser(@PathVariable("userId") String userId, @RequestBody String userInfo){


        String password = "";

        try {
            JSONObject userJSON = new JSONObject(userInfo);
            password = userJSON.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UnregisterInput input = unregisterUseCase.newInput();
        input.setUserId(userId);
        input.setPassword(password);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        unregisterUseCase.execute(input, presenter);

        if(presenter.getExitCode() == ExitCode.SUCCESS) {
            return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.OK);
        }

        return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
