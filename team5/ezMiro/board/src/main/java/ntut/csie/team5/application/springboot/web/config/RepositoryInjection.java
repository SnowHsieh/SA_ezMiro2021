package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("EzMiroRepositoryInjection")
public class RepositoryInjection {

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl();
    }

    @Bean(name = "figureRepository")
    public FigureRepository figureRepository() {
        return new FigureRepositoryImpl();
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}
