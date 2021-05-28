package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.line.LineRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.line.LineRepositoryPeer;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.note.NoteRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.note.NoteRepositoryPeer;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;
import ntut.csie.team5.usecase.figure.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("EzMiroRepositoryInjection")
public class RepositoryInjection {

    private BoardRepositoryPeer boardRepositoryPeer;
    private NoteRepositoryPeer noteRepositoryPeer;
    private LineRepositoryPeer lineRepositoryPeer;


    @Bean(name = "boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name = "noteRepository")
    public NoteRepository noteRepository() {
        return new NoteRepositoryImpl(noteRepositoryPeer);
    }

    @Bean(name = "lineRepository")
    public LineRepository lineRepository() {
        return new LineRepositoryImpl(lineRepositoryPeer);
    }

    @Bean(name = "ezMiroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }

    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
        this.boardRepositoryPeer = boardRepositoryPeer;
    }

    @Autowired
    public void setNoteRepositoryPeer(NoteRepositoryPeer noteRepositoryPeer) {
        this.noteRepositoryPeer = noteRepositoryPeer;
    }

    @Autowired
    public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer) {
        this.lineRepositoryPeer = lineRepositoryPeer;
    }
}
