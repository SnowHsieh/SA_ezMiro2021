package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteInput;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteNoteUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;
    private NotifyBoardAdapter notifyBoardAdapter;


    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
        boardRepository = new BoardRepositoryImpl();
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(figureRepository, boardRepository, domainEventBus));
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void delete_note() {
        String noteId = create_note();
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCaseImpl(figureRepository, domainEventBus);
        DeleteNoteInput input = deleteNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setBoardId("boardId");

        deleteNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(0, figureRepository.findAll().size());
        assertFalse(figureRepository.findById(output.getId()).isPresent());
        assertEquals(0, boardRepository.findById("boardId").get().getCommittedFigures().size());
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