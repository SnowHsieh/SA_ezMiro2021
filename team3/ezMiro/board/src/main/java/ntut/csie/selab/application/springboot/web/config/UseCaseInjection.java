package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.adapter.controller.rest.springboot.widget.editTextOfStickyNote.EditFontSizeOfStickyNote;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.edit.color.ChangeColorOfStickyNoteUseCase;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetUseCase;
import ntut.csie.selab.usecase.widget.edit.fontsize.EditFontSizeOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.edit.text.EditTextOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetUseCase;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("MiroUseCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private WidgetRepository widgetRepository;
    private DomainEventBus eventBus;

    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name="GetBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCase(boardRepository, widgetRepository);
    }

    @Bean(name="CreateBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCase(boardRepository, eventBus);
    }

    @Bean(name="CreateStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() { return new CreateStickyNoteUseCase(widgetRepository, eventBus); }

    @Bean(name="MoveStickyNoteUseCase")
    public MoveStickyNoteUseCase moveStickyNoteUseCase() {
        return new MoveStickyNoteUseCase(widgetRepository, eventBus);
    }

    @Bean(name="ChangeColorOfStickyNoteUseCase")
    public ChangeColorOfStickyNoteUseCase changeColorOfStickyNoteUseCase() { return new ChangeColorOfStickyNoteUseCase(widgetRepository, eventBus); }

    @Bean(name="ResizeStickyNoteUseCase")
    public ResizeStickyNoteUseCase resizeStickyNoteUseCase() { return new ResizeStickyNoteUseCase(widgetRepository, eventBus); }

    @Bean(name="GetWidgetUseCase")
    public GetWidgetUseCase getWidgetUseCase() {
        return new GetWidgetUseCase(widgetRepository);
    }

    @Bean(name="EditTextOfStickyNoteUseCase")
    public EditTextOfStickyNoteUseCase editTextOfStickyNoteUseCase() {
        return new EditTextOfStickyNoteUseCase(widgetRepository, eventBus);
    }

    @Bean(name="DeleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(widgetRepository, eventBus);
    }

    @Bean(name="ChangeZOrderOfWidgetUseCase")
    public ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase() {
        return new ChangeZOrderOfWidgetUseCase(boardRepository, eventBus);
    }

    @Bean("EditFontSizeOfStickyNoteUseCase")
    public EditFontSizeOfStickyNoteUseCase editFontSizeOfStickyNoteUseCase() {
        return new EditFontSizeOfStickyNoteUseCase(widgetRepository, eventBus);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setWidgetRepository(WidgetRepository widgetRepository) { this.widgetRepository = widgetRepository; }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }
}
