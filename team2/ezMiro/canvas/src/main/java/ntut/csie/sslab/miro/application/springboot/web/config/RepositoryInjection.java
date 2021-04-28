package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

    private BoardRepository boardRepository;
    private FigureRepository figureRepository;

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Bean(name="boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl();
    }

    @Bean(name="figureRepository")
    public FigureRepository figureRepository() {
        return new FigureRepositoryImpl();
    }

    @Bean(name="miroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}
