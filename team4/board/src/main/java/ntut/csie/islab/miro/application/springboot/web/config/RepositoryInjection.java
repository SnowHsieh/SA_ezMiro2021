package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepository();
    }

    @Bean(name = "textFigureRepository")
    public TextFigureRepository textFigureRepository() {
        return new TextFigureRepository();
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }


}
