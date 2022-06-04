package ntut.csie.islab.account.users.usecase.login;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.Optional;

public class LoginUseCaseImpl implements LoginUseCase {
    private UserRepository userRepository;
    private DomainEventBus domainEventBus;

    public LoginUseCaseImpl(UserRepository userRepository, DomainEventBus domainEventBus) {
        this.userRepository = userRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(LoginUseCaseInput input, CqrsCommandOutput output) {
        boolean usernameExist = isUsernameExist(input.getUsername());
        Optional<User> optionalUser = userRepository.getUserByUsername(input.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(input.getPassword())) {
                user.loginSuccess();
                domainEventBus.postAll(user);
                output.setId(user.getId());
                output.setExitCode(ExitCode.SUCCESS);
            }
            // no fail
        } else {
            //一定找的到
        }


    }


    private boolean isUsernameExist(String username) {
        return userRepository.getUserByUsername(username).orElse(null) != null;
    }

    public LoginUseCaseInput newInput() {
        return new LoginInputImpl();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    private static class LoginInputImpl implements LoginUseCaseInput {

        private String username;
        private String email;
        private String password;

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public void setUsername(String username) {
            this.username = username;
        }


        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public void setPassword(String password) {
            this.password = password;
        }
    }

}
