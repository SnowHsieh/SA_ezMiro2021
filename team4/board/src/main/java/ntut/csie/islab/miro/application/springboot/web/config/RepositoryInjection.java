package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.gateway.repository.textfigure.stickynote.StickyNoteRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.textfigure.stickynote.StickyNoteRepositoryPeer;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.usecase.textfigure.StickyNoteRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("EZMiroRepositoryInjection")
public class RepositoryInjection {

    private BoardRepositoryPeer boardRepositoryPeer ;
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer ;

    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer){
        this.boardRepositoryPeer = boardRepositoryPeer;
    }

    @Autowired
    public void setStickyNoteRepositoryPeer(StickyNoteRepositoryPeer stickyNoteRepositoryPeer){
        this.stickyNoteRepositoryPeer = stickyNoteRepositoryPeer;
    }


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
