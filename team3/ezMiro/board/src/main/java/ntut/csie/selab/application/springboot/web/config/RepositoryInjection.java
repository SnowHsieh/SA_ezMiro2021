package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.CommittedWidgetRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

    private WidgetRepositoryPeer widgetRepositoryPeer;

    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    public void setWidgetRepositoryPeer(WidgetRepositoryPeer widgetRepositoryPeer) {
        this.widgetRepositoryPeer = widgetRepositoryPeer;
    }

    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
        this.boardRepositoryPeer = boardRepositoryPeer;
    }

    @Bean(name="boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name="widgetRepository")
    public WidgetRepository widgetRepository() { return new WidgetRepositoryImpl(widgetRepositoryPeer); }

    @Bean(name="miroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}
