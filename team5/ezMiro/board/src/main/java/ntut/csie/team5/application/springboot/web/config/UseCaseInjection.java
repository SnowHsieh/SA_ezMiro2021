package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {

    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private DomainEventBus eventBus;

    @Bean(name = "createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name = "getBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCaseImpl(boardRepository, figureRepository, eventBus);
    }

    @Bean(name = "postNoteUseCase")
    public PostNoteUseCase postNoteUseCase() {
        return new PostNoteUseCaseImpl(figureRepository, eventBus);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }
}
