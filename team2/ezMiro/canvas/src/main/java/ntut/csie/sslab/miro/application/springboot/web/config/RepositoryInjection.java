package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.cursor.CursorRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.LineRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.NoteRepositoryPeer;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private NoteRepositoryPeer noteRepositoryPeer;

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Bean(name="boardRepository")
    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(boardRepositoryPeer);
    }

    @Bean(name="figureRepository")
    public FigureRepository figureRepository() {
        return new FigureRepositoryImpl(noteRepositoryPeer, lineRepositoryPeer);
    }

    @Bean(name="cursorRepository")
    public CursorRepository cursorRepository() {
        return new CursorRepositoryImpl();
    }

    @Bean(name="miroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}