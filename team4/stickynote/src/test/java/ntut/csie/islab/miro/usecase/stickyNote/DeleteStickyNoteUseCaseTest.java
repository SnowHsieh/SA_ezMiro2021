package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.entity.figure.ShapeKindEnum;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ntut.csie.islab.miro.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.entity.figure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.figure.Style;

public class DeleteStickyNoteUseCaseTest {
    public DomainEventBus domainEventBus;
    public FigureRepository stickyNoteRepository;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new FigureRepository();
    }

    @Test
    public void test_delete_sticky_note(){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput createStickyNoteInput = createStickyNoteUseCase.newInput();
        CqrsCommandPresenter createStickyNoteOutput = CqrsCommandPresenter.newInstance();
        createStickyNoteInput.setBoardId(UUID.randomUUID());
        createStickyNoteInput.setPosition(1.0,1.0);
        createStickyNoteInput.setContent("Content");
        createStickyNoteInput.setStyle(new Style(12, ShapeKindEnum.CIRCLE, 87.87, "#948700"));
        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);

        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = deleteStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setStickyNoteId(UUID.fromString(createStickyNoteOutput.getId()));
        deleteStickyNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        assertEquals(null, stickyNoteRepository.findById(UUID.fromString(output.getId())).orElse(null));
    }

}