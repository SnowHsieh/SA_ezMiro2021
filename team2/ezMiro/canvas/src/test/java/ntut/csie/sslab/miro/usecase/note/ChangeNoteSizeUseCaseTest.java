package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeInput;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteSizeUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void change_note_size() {
        String noteId = create_note();
        ChangeNoteSizeUseCase changeNoteSizeUseCase = new ChangeNoteSizeUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteSizeInput input = changeNoteSizeUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setHeight(200);
        input.setWidth(200);

        changeNoteSizeUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(200,  figureRepository.findById(output.getId()).get().getHeight());
        assertEquals(200,  figureRepository.findById(output.getId()).get().getWidth());
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