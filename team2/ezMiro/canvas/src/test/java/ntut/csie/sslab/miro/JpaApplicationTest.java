package ntut.csie.sslab.miro;

import ntut.csie.sslab.miro.application.springboot.web.CanvasMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@ComponentScan(basePackages={"ntut.csie.sslab.miro"}, excludeFilters= {
@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= CanvasMain.class)})
@EntityScan(basePackages={"ntut.csie.sslab.miro"})
@SpringBootApplication
public abstract class JpaApplicationTest {
}
