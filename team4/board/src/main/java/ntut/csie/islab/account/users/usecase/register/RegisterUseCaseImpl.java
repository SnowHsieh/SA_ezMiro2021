package ntut.csie.islab.account.users.usecase.register;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.UUID;

public class RegisterUseCaseImpl implements RegisterUseCase {
    private UserRepository userRepository;
    private DomainEventBus domainEventBus;

    public RegisterUseCaseImpl(UserRepository userRepository, DomainEventBus domainEventBus) {
        this.userRepository = userRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(RegisterInput input, CqrsCommandOutput output) {
        boolean usernameExist = isUsernameExist(input.getUsername());
        boolean emailExist = isEmailExist(input.getEmail());

        User user = new User(UUID.randomUUID().toString(), input.getUsername(), input.getEmail(), input.getPassword());

        userRepository.save(user);
        domainEventBus.postAll(user);

        output.setId(user.getId());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Register success");
    }


    private boolean isUsernameExist(String username) {
        return userRepository.getUserByUsername(username).orElse(null) != null;
    }

    private boolean isEmailExist(String email) {
        return userRepository.getUserByEmail(email).orElse(null) != null;
    }

    @Override
    public RegisterInput newInput() {
        return new RegisterInputImpl();
    }

    private static class RegisterInputImpl implements RegisterInput {

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
        public String getEmail() {
            return email;
        }

        @Override
        public void setEmail(String email) {
            this.email = email;
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
