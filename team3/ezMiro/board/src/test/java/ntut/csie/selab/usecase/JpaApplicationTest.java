package ntut.csie.selab.usecase;

import ntut.csie.selab.application.springboot.web.EzMiroWebMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@ComponentScan(basePackages={"ntut.csie.selab"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= EzMiroWebMain.class)})
@EntityScan(basePackages={"ntut.csie.selab"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}
