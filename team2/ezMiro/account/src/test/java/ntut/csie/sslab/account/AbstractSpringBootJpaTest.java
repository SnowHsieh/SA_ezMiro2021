package ntut.csie.sslab.account;

import ntut.csie.sslab.account.users.command.adapter.encyption.BCryptImpl;
import ntut.csie.sslab.account.users.command.adapter.repository.UserRepositoryImpl;
import ntut.csie.sslab.account.users.command.adapter.repository.UserRepositoryPeer;
import ntut.csie.sslab.account.users.command.entity.Role;
import ntut.csie.sslab.account.users.command.usecase.Encrypt;
import ntut.csie.sslab.account.users.command.usecase.UserRepository;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCase;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCaseImpl;
import ntut.csie.sslab.account.users.query.adapter.repository.UserQueryRepositoryImpl;
import ntut.csie.sslab.account.users.query.usecase.UserQueryRepository;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCase;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCaseImpl;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterInput;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCase;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCaseImpl;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
// 加上這個就不會每次跑完test method自動 rollback，才可以看到測試資料被加入資料庫
//@Rollback(false)
public abstract class AbstractSpringBootJpaTest {

    public static final long WAITING = 1000;
    public DomainEventBus domainEventBus;
    public UserRepository userRepository;
    public UserQueryRepository userQueryRepository;
    public Encrypt encrypt;
    public String username;
    public String password;
    public String nickname;
    public String email;
    public String role;
    public boolean isThirdParty;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepositoryPeer userRepositoryPeer;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepositoryImpl(userRepositoryPeer);
        userQueryRepository = new UserQueryRepositoryImpl(jdbcTemplate);

        domainEventBus = new DomainEventBus();
        encrypt = new BCryptImpl();

        username = UUID.randomUUID().toString();
        password = "123456";
        nickname = "ezKanban";
        email = UUID.randomUUID().toString();
        role = Role.User.toString();
        isThirdParty = false;

    }

    public RegisterUseCase newRegisterUseCase() { return new RegisterUseCaseImpl(userRepository, domainEventBus, encrypt);}

    public UnregisterUseCase newUnregisterUseCase() { return new UnregisterUseCaseImpl(userRepository, domainEventBus, encrypt);}

    public GetUserUseCase newGetUserUseCase(){
        return new GetUserUseCaseImpl(userQueryRepository);
    }

    public String registerUser(String username, String password, String email, String nickname, String role, boolean isThirdParty) {
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(isThirdParty);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        return output.getId();
    }

}
