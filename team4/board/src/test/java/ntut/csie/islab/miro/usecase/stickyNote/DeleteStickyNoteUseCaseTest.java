package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.DeleteStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.DeleteStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DeleteStickyNoteUseCaseTest {
    public DomainEventBus domainEventBus;
    public TextFigureRepository stickyNoteRepository;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new TextFigureRepository();
    }

    @Test
    public void test_delete_sticky_note(){

        UUID boardId = UUID.randomUUID();
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput createStickyNoteInput = createStickyNoteUseCase.newInput();
        CqrsCommandPresenter createStickyNoteOutput = CqrsCommandPresenter.newInstance();

        createStickyNoteInput.setBoardId(boardId);
        createStickyNoteInput.setPosition(1.0,1.0,0);
        createStickyNoteInput.setContent("Content");
        createStickyNoteInput.setStyle(new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));
        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);
        assertNotNull(stickyNoteRepository.findById(boardId, UUID.fromString(createStickyNoteOutput.getId())).get());

        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = deleteStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(createStickyNoteOutput.getId()));
        assertNotNull(stickyNoteRepository.findById(boardId, input.getFigureId()).get());
        deleteStickyNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        assertEquals(null, stickyNoteRepository.findById(boardId,UUID.fromString(output.getId())).orElse(null));
    }

}