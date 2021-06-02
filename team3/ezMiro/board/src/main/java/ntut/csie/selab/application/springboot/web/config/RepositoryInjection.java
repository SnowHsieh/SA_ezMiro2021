package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    private LineRepositoryPeer lineRepositoryPeer;

    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    public void setWidgetRepositoryPeer(StickyNoteRepositoryPeer stickyNoteRepositoryPeer) {
        this.stickyNoteRepositoryPeer = stickyNoteRepositoryPeer;
    }

    @Autowired
    public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer) {
        this.lineRepositoryPeer = lineRepositoryPeer;
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
    public StickyNoteRepository widgetRepository() { return new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer); }

    @Bean(name="lineRepository")
    public LineRepository lineRepository() { return new LineRepositoryImpl(lineRepositoryPeer); }

    @Bean(name="miroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}
