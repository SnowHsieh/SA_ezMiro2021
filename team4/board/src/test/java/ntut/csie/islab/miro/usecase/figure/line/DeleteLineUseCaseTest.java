package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteLineUseCaseTest extends AbstractSpringBootJpaTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_delete_line_use_case() {
        //Create a Line in the board
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(0, 50));
        positionList.add(new Position(100, 150));
        CqrsCommandPresenter newLine = generateCreateLineUseCaseOutput(
                boardId,
                positionList,
                5,
                "#000000"
        );

        DeleteLineUseCase deleteLineUseCase = new DeleteLineUseCase(domainEventBus, lineRepository);
        DeleteLineInput input = deleteLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(newLine.getId()));
        deleteLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        board = boardRepository.findById(board.getBoardId()).get();
        assertEquals(1, board.getCommittedFigures().size());
    }
}
