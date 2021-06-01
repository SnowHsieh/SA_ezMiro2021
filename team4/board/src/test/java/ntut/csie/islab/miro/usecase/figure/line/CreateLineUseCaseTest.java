package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.create.CreateLineInput;
import ntut.csie.islab.miro.usecase.figure.line.create.CreateLineUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateLineUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_create_line_usecase() {
        CreateLineUseCase createLineUseCase = new CreateLineUseCase(lineRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(0, 50));
        positionList.add(new Position(100, 150));
        int strokeWidth = 5;
        String color = "#000000";

        input.setBoardId(boardId);
        input.setPositionList(positionList);
        input.setStrokeWidth(strokeWidth);
        input.setColor(color);
        createLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Line resultLine = lineRepository.findById(UUID.fromString(output.getId())).get();
        assertNotNull(resultLine);
        assertEquals(boardId, resultLine.getBoardId());
        assertEquals(0, resultLine.getPositionList().get(0).getX());
        assertEquals(50, resultLine.getPositionList().get(0).getY());
        assertEquals(100, resultLine.getPositionList().get(1).getX());
        assertEquals(150, resultLine.getPositionList().get(1).getY());
        assertEquals(strokeWidth, resultLine.getStrokeWidth());
        assertEquals(color, resultLine.getColor());

        board = boardRepository.findById(board.getBoardId()).get();
        assertEquals(1, board.getCommittedFigures().size());

    }

}
