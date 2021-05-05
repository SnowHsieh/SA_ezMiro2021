package ntut.csie.sslab.miro.usecase.scenario;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorInput;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CreateEventStormingNotesScenarioTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();

        // TODO : should be deleted after demo
        for (Figure figure : figureRepository.findAll()) {
            figureRepository.deleteById(figure.getId());
        }
    }

    @Test
    public void create_event_storming_notes() {
        Coordinate readModelPosition = new Coordinate(100, 100);
        String readModelId = create_note("boardId", readModelPosition);
        change_color(readModelId, "#C9DF56");
        change_description(readModelId, "BoardId\nCoordinate");

        Coordinate commandPosition = new Coordinate(220, 100);
        String commandId = create_note("boardId", commandPosition);
        change_color(commandId, "#6CD8FA");
        change_description(commandId, "CreateNote");

        Coordinate domainEventPosition = new Coordinate(340, 100);
        String domainEventId = create_note("boardId", domainEventPosition);
        change_color(domainEventId, "#FF9D48");
        change_description(domainEventId, "NoteCreated");

        Coordinate aggregatePosition = new Coordinate(280, 20);
        String aggregateId = create_note("boardId", aggregatePosition);
        change_color(aggregateId, "#FFF9B1");
        change_description(aggregateId, "Figure");

        Coordinate actorPosition = new Coordinate(150, 180);
        String actorId = create_note("boardId", actorPosition);
        change_color(actorId, "#FEF445");
        change_description(actorId, "User");

        List<Figure> figures = figureRepository.findByBoardId("boardId");
        assertEquals(5, figures.size());
        Note readModelNote = (Note) figures.stream().filter(x -> x.getId().equals(readModelId)).findFirst().get();
        assertEquals(readModelPosition, readModelNote.getCoordinate());
        assertEquals(100, readModelNote.getWidth());
        assertEquals(100, readModelNote.getHeight());
        assertEquals("#C9DF56", readModelNote.getColor());
        assertEquals("BoardId\nCoordinate", readModelNote.getDescription());
        Note commandNote = (Note) figures.stream().filter(x -> x.getId().equals(commandId)).findFirst().get();
        assertEquals(commandPosition, commandNote.getCoordinate());
        assertEquals(100, commandNote.getWidth());
        assertEquals(100, commandNote.getHeight());
        assertEquals("#6CD8FA", commandNote.getColor());
        assertEquals("CreateNote", commandNote.getDescription());
        Note domainEventNote = (Note) figures.stream().filter(x -> x.getId().equals(domainEventId)).findFirst().get();
        assertEquals(domainEventPosition, domainEventNote.getCoordinate());
        assertEquals(100, domainEventNote.getWidth());
        assertEquals(100, domainEventNote.getHeight());
        assertEquals("#FF9D48", domainEventNote.getColor());
        assertEquals("NoteCreated", domainEventNote.getDescription());
        Note aggregateNote = (Note) figures.stream().filter(x -> x.getId().equals(aggregateId)).findFirst().get();
        assertEquals(aggregatePosition, aggregateNote.getCoordinate());
        assertEquals(100, aggregateNote.getWidth());
        assertEquals(100, aggregateNote.getHeight());
        assertEquals("#FFF9B1", aggregateNote.getColor());
        assertEquals("Figure", aggregateNote.getDescription());
        Note actorNote = (Note) figures.stream().filter(x -> x.getId().equals(actorId)).findFirst().get();
        assertEquals(actorPosition, actorNote.getCoordinate());
        assertEquals(100, actorNote.getWidth());
        assertEquals(100, actorNote.getHeight());
        assertEquals("#FEF445", actorNote.getColor());
        assertEquals("User", actorNote.getDescription());
    }

    private void change_description(String noteId, String description) {
        ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase = new ChangeNoteDescriptionUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteDescriptionInput input = changeNoteDescriptionUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setDescription(description);

        changeNoteDescriptionUseCase.execute(input, output);

        Note note = (Note)figureRepository.findById(output.getId()).get();
        assertEquals(description, note.getDescription());
        assertNotNull(output.getId());
    }

    private String create_note(String boardId, Coordinate coordinate){
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(coordinate);

        createNoteUseCase.execute(input, output);

        return output.getId();
    }

    private void change_color(String noteId, String color) {
        ChangeNoteColorUseCase changeNoteColorUseCase = new ChangeNoteColorUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setColor(color);

        changeNoteColorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(color, figureRepository.findById(output.getId()).get().getColor());
    }
}