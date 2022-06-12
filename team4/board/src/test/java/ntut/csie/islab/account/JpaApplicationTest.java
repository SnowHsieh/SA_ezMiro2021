package ntut.csie.islab.account;

import ntut.csie.islab.miro.application.springboot.web.EZMiroWebMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages={"ntut.csie.sslab.account"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= EZMiroWebMain.class)})
@EntityScan(basePackages={"ntut.csie.sslab.account"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}