package ntut.csie.sslab.account.application.springboot.web.config;

import ntut.csie.sslab.account.users.command.usecase.Encrypt;
import ntut.csie.sslab.account.users.command.usecase.UserRepository;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCase;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCaseImpl;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCase;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCaseImpl;
import ntut.csie.sslab.account.users.query.usecase.UserQueryRepository;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCase;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCaseImpl;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("AccountUseCaseInjection")
public class UseCaseInjection {

    private DomainEventBus eventBus;
    private UserRepository userRepository;
    private UserQueryRepository userQueryRepository;
    private Encrypt encrypt;

    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserQueryRepository(UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @Autowired
    public void setEncrypt(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    @Bean
    public RegisterUseCase registerUseCase(){
        return new RegisterUseCaseImpl(userRepository, eventBus, encrypt);
    }

    @Bean
    public UnregisterUseCase unregisterUseCase(){
        return new UnregisterUseCaseImpl(userRepository, eventBus, encrypt);
    }

    @Bean
    public GetUserUseCase getUserUseCase() {
        return new GetUserUseCaseImpl(userQueryRepository);
    }

}
