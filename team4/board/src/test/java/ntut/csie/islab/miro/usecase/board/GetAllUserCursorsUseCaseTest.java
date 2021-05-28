package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.getallcursors.AllCursorsViewModel;
import ntut.csie.islab.miro.adapter.presenter.getallcursors.GetAllUserCursorsPresenter;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.getallusercursors.GetAllUserCursorsInput;
import ntut.csie.islab.miro.usecase.board.getallusercursors.GetAllUserCursorsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllUserCursorsUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_get_all_user_cursors() {
        GetAllUserCursorsUseCase getAllUserCursorsUseCase = new GetAllUserCursorsUseCase(domainEventBus, boardRepository);
        GetAllUserCursorsInput input = getAllUserCursorsUseCase.newInput();
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
