package ntut.csie.sslab.account.application.springboot.web.config;

import ntut.csie.sslab.account.users.command.adapter.encyption.BCryptImpl;
import ntut.csie.sslab.account.users.command.adapter.repository.UserRepositoryImpl;
import ntut.csie.sslab.account.users.command.adapter.repository.UserRepositoryPeer;
import ntut.csie.sslab.account.users.command.usecase.Encrypt;
import ntut.csie.sslab.account.users.command.usecase.UserRepository;
import ntut.csie.sslab.account.users.query.adapter.repository.UserQueryRepositoryImpl;
import ntut.csie.sslab.account.users.query.usecase.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;


@PropertySource(value = "classpath:/application.properties")
@Configuration("AccountRepositoryInjection")
public class RepositoryInjection {
    private UserRepositoryPeer userRepositoryPeer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setUserRepositoryPeer(UserRepositoryPeer userRepositoryPeer) {
        this.userRepositoryPeer = userRepositoryPeer;
    }

    @Bean
    public UserRepository userRepository(){ return new UserRepositoryImpl(userRepositoryPeer); }

    @Bean
    public UserQueryRepository userQueryRepository(){ return new UserQueryRepositoryImpl(jdbcTemplate); }

    @Bean
    public Encrypt encrypt(){ return new BCryptImpl();}


}
