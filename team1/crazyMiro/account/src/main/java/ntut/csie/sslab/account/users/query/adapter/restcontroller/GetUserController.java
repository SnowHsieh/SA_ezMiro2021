package ntut.csie.sslab.account.users.query.adapter.restcontroller;

import ntut.csie.sslab.account.users.query.adapter.presenter.get.GetUserPresenter;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserInput;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCase;
import ntut.csie.sslab.account.users.query.adapter.presenter.get.UserViewModel;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserController {
    private GetUserUseCase getUserUseCase;

    @Autowired
    public void setGetUserUseCase(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping(path = "${ACCOUNT_PREFIX}/users/{userId}", produces = "application/json")
    public ResponseEntity<UserViewModel> getUser(@PathVariable("userId") String userId){

        GetUserInput input = getUserUseCase.newInput();
        input.setUserId(userId);

        GetUserPresenter presenter = new GetUserPresenter();

        getUserUseCase.execute(input, presenter);

        if(presenter.getExitCode() == ExitCode.SUCCESS){
            return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.OK);
        }
        return new ResponseEntity<>(presenter.buildViewModel(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
