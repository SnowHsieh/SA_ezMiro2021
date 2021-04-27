package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {

    @Bean(name = "figureRepository")
    public FigureRepository figureRepository() {
        return new FigureRepository();
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }

}
