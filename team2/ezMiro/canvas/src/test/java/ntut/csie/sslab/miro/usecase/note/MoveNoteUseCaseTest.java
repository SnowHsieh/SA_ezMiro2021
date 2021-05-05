package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteInput;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveNoteUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void move_note() {
        String noteId = create_note();
        MoveNoteUseCase moveNoteUseCase = new MoveNoteUseCaseImpl(figureRepository, domainEventBus);
        MoveNoteInput input = moveNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        Coordinate coordinate = new Coordinate(10,6);
        input.setNoteId(noteId);
        input.setCoordinate(coordinate);

        moveNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(coordinate, figureRepository.findById(output.getId()).get().getCoordinate());
    }

    private String create_note() {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId("boardId");
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        return output.getId();
    }
}