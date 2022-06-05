package ntut.csie.islab.miro.usecase;


import ntut.csie.islab.miro.application.springboot.web.EZMiroWebMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages={"ntut.csie.islab.miro", "ntut.csie.islab.account", "ntut.csie.islab.team"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= EZMiroWebMain.class)})
@EntityScan(basePackages={"ntut.csie.islab.miro", "ntut.csie.islab.account", "ntut.csie.islab.team"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}
