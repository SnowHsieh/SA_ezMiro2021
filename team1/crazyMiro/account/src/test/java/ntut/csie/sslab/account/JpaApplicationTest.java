package ntut.csie.sslab.account;

import ntut.csie.sslab.account.application.springboot.web.AccountWebMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"ntut.csie.sslab.account"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= AccountWebMain.class)})
@EntityScan(basePackages={"ntut.csie.sslab.account"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}
