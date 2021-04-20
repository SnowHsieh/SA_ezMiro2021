package ntut.csie.sslab.account.users.query.adapter.presenter.get;

import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserOutput;
import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.ddd.usecase.Result;

public class GetUserPresenter extends Result implements Presenter<UserViewModel>, GetUserOutput {

    private String id;
    private UserViewModel viewModel;

    public GetUserPresenter() {
        this.viewModel = new UserViewModel();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public GetUserOutput setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public Output setMessage(String message) {
        viewModel.setMessage(message);
        return super.setMessage(message);
    }

    @Override
    public UserDto getUser() {
        return viewModel.getUser();
    }

    @Override
    public void setUser(UserDto userDto) {
        viewModel.setUser(userDto);
    }

    @Override
    public UserViewModel buildViewModel() {
        return viewModel;
    }
}
