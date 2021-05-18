package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getAllCursors.AllCursorsViewModel;
import ntut.csie.islab.miro.adapter.presenter.getAllCursors.GetAllUserCursorsPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
import ntut.csie.islab.miro.usecase.textFigure.TextFigureDto;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.EditStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.EditStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllUserCursorsUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    public NotifyBoardAdapter notifyBoardAdapter;

    @BeforeEach
    public void setUp() {
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(boardRepository, domainEventBus));
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_get_all_user_cursors() {
        GetAllUserCursorsUseCase getAllUserCursorsUseCase = new GetAllUserCursorsUseCase(domainEventBus, boardRepository);
        GetAllUserCursorsInput input = getAllUserCursorsUseCase.newInput();
        UUID boardId = UUID.randomUUID();
        input.setBoardId(boardId);
        GetAllUserCursorsPresenter presenter = new GetAllUserCursorsPresenter();

        getAllUserCursorsUseCase.execute(input, presenter);

        assertEquals(boardId, presenter.getBoardId());
        assertEquals(0, presenter.getCursorDtos().size());

        AllCursorsViewModel getAllUserCursorsViewModel = presenter.buildViewModel();

        assertEquals(boardId, getAllUserCursorsViewModel.getBoardId());
        assertEquals(0, getAllUserCursorsViewModel.getCursorDtos().size());

    }


}
