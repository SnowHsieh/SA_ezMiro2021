package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.board.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DeleteStickyNoteUseCaseTest {
    public DomainEventBus domainEventBus;
    public TextFigureRepository stickyNoteRepository;
    private CqrsCommandPresenter preGeneratedStickyNote;
    private CqrsCommandPresenter preGeneratedBoard;
    private Board board;
    public NotifyBoardAdapter notifyBoardAdapter;
    public BoardRepository boardRepository;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new TextFigureRepository();
        boardRepository = new BoardRepository();
        this.preGeneratedFactory();
        board = boardRepository.findById(UUID.fromString(preGeneratedBoard.getId())).get();
        board.commitFigure(UUID.fromString(preGeneratedStickyNote.getId()));
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(boardRepository, domainEventBus));
        domainEventBus.register(notifyBoardAdapter);
    }
    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position, String content, Style style){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(id);
        input.setPosition(position.getX(),position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);

        return output;
    }

    private CqrsCommandPresenter generateCreateBoardUseCaseOutput(String boardName){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(domainEventBus, boardRepository);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardInput.setTeamId(UUID.randomUUID());
        createBoardInput.setBoardName(boardName);
        createBoardUseCase.execute(createBoardInput, createBoardUseCaseoutput);
        return createBoardUseCaseoutput;
    }

    private void preGeneratedFactory(){
        preGeneratedBoard = generateCreateBoardUseCaseOutput("EventStorming");
        preGeneratedStickyNote = generateCreateStickyNoteUseCaseOutput(
                UUID.fromString(preGeneratedBoard.getId()),
                new Position(1.0,1.0),
                "Content",
                new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));
    }

    @Test
    public void test_delete_sticky_note(){
        UUID boardId = UUID.fromString(preGeneratedBoard.getId());
        UUID preGeneratedStickyNoteId =  UUID.fromString(preGeneratedStickyNote.getId());
        assertNotNull(stickyNoteRepository.findById(boardId, preGeneratedStickyNoteId).get());
        //check stickynote created and committed to board.
        assertEquals(1,board.getCommittedFigures().size());

        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = deleteStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(preGeneratedStickyNoteId);
        assertNotNull(stickyNoteRepository.findById(boardId, input.getFigureId()).get());
        deleteStickyNoteUseCase.execute(input, output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals(null, stickyNoteRepository.findById(boardId,UUID.fromString(output.getId())).orElse(null));
        //check stickynote uncommitted to board.
        assertEquals(0,board.getCommittedFigures().size());

    }

}