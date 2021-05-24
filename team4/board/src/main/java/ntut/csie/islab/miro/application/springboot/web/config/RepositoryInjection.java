package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote.StickyNoteRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote.StickyNoteRepositoryPeer;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {

    private BoardRepositoryPeer boardRepositoryPeer ;
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer ;

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name = "stickyNoteRepository")
    public StickyNoteRepository stickyNoteRepository() {
        return new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }

}
