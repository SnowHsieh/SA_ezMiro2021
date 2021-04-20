package ntut.csie.sslab.account.users.query.adapter.presenter.get;

import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;

public class UserViewModel implements ViewModel {

    private String message;

    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
