package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.GetBoardContentUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.DeleteStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.MoveStickyNoteUseCase;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private TextFigureRepository textFigureRepository;
    private DomainEventBus eventBus;

    @Bean(name = "createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCase(eventBus, boardRepository);
    }

    @Bean(name = "getBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCase(eventBus,boardRepository, textFigureRepository);
    }

    @Bean(name = "createStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() {
        return new CreateStickyNoteUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "editStickyNoteUseCase")
    public MoveStickyNoteUseCase editStickyNoteUseCase() {
        return new MoveStickyNoteUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "deleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(textFigureRepository,eventBus);
    }



    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(TextFigureRepository textFigureRepository) {
        this.textFigureRepository = textFigureRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }


}
