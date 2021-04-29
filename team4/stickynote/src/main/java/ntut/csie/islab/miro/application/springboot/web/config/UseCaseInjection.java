package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.stickyNote.DeleteStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.stickyNote.EditStickyNoteUseCase;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {
    private FigureRepository figureRepository;
    private DomainEventBus eventBus;

    @Bean(name = "createStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() {
        return new CreateStickyNoteUseCase(figureRepository,eventBus);
    }

    @Bean(name = "editStickyNoteUseCase")
    public EditStickyNoteUseCase editStickyNoteUseCase() {
        return new EditStickyNoteUseCase(figureRepository,eventBus);
    }

    @Bean(name = "deleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(figureRepository,eventBus);
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
