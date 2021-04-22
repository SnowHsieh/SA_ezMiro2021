package ntut.csie.islab.miro.usecase.stickyNote;


import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.islab.miro.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.entity.figure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.figure.Style;
import java.util.UUID;

public class CreateStickyNoteUseCaseTest {
    public DomainEventBus domainEventBus;
    public FigureRepository stickyNoteRepository;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new FigureRepository();
    }

    @Test
    public void test_create_sticky_note(){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(UUID.randomUUID());
        input.setPosition(1.0,1.0);
        input.setContent("Content");
        input.setStyle(new Style(12, ShapeKindEnum.CIRCLE, 87.87, "#948700"));
        createStickyNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        assertEquals("Content", stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getContent());
    }

}