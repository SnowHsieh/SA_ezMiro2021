package ntut.csie.team5.usecase;


import ntut.csie.team5.application.springboot.web.EzMiroWebMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages={"ntut.csie.team5"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= EzMiroWebMain.class)})
@EntityScan(basePackages={"ntut.csie.team5"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}
