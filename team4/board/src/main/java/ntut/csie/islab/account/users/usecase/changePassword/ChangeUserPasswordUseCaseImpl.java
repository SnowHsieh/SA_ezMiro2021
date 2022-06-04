package ntut.csie.islab.account.users.usecase.changePassword;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.Optional;

public class ChangeUserPasswordUseCaseImpl implements ChangeUserPasswordUseCase {
    private UserRepository userRepository;
    private DomainEventBus domainEventBus;

    public ChangeUserPasswordUseCaseImpl(UserRepository userRepository, DomainEventBus domainEventBus) {
        this.userRepository = userRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeUserPasswordUseCaseInput input, CqrsCommandOutput output) {
        Optional<User> optionalUser = userRepository.getUserByUsername(input.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setPassword(input.getPassword());
            userRepository.save(user);
            domainEventBus.postAll(user);
            output.setId(user.getId());
            output.setExitCode(ExitCode.SUCCESS);
            // no fail
        }
    }


    private boolean isUsernameExist(String username) {
        return userRepository.getUserByUsername(username).orElse(null) != null;
    }

    public ChangeUserPasswordUseCaseInput newInput() {
        return new ChangeUserPasswordUseCaseInputImpl();
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

    private static class ChangeUserPasswordUseCaseInputImpl implements ChangeUserPasswordUseCaseInput {

        private String username;
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
