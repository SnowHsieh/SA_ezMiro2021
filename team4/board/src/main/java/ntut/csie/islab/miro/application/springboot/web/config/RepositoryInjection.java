package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.account.users.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.line.LineRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.line.LineRepositoryPeer;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.connectablefigure.stickynote.StickyNoteRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.connectablefigure.stickynote.StickyNoteRepositoryPeer;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.StickyNoteRepository;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.repository.TeamRepositoryImpl;
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
    private LineRepositoryPeer lineRepositoryPeer;

    @Autowired
    public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer){
        this.lineRepositoryPeer = lineRepositoryPeer;
    }

    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer){
        this.boardRepositoryPeer = boardRepositoryPeer;
    }

    @Autowired
    public void setStickyNoteRepositoryPeer(StickyNoteRepositoryPeer stickyNoteRepositoryPeer){
        this.stickyNoteRepositoryPeer = stickyNoteRepositoryPeer;
    }

    @Bean(name = "lineRepository")
    public LineRepository lineRepository() {
        return new LineRepositoryImpl(lineRepositoryPeer);
    }

    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name = "userRepository")
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean(name = "teamRepository")
    public TeamRepository teamRepository() {
        return new TeamRepositoryImpl();
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
