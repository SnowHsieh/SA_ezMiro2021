package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepository();
    }

    @Bean(name = "figureRepository")
    public FigureRepository figureRepository() {
        return new FigureRepository();
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }


}
