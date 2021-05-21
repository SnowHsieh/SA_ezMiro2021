package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepositoryImpl;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Order(1)
@PropertySource(value = "classpath:/application.properties")
@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {
    private BoardRepositoryPeer boardRepositoryPeer;

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name = "textFigureRepository")
    public TextFigureRepository textFigureRepository() {
        return new TextFigureRepository();
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }

//    @Autowired
//    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
//        this.boardRepositoryPeer = boardRepositoryPeer;
//    }


}
