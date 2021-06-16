package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.changepath.ChangeLinePathInput;
import ntut.csie.islab.miro.usecase.figure.line.changepath.ChangeLinePathUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeLinePathUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_change_line_path_usecase() {
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(0, 50));
        positionList.add(new Position(100, 150));
        CqrsCommandPresenter newLine = generateCreateLineUseCaseOutput(
                boardId,
                positionList,
                5,
                "#000000"
        );


        ChangeLinePathUseCase changeLinePathUseCase = new ChangeLinePathUseCase(domainEventBus,lineRepository);
        ChangeLinePathInput input = changeLinePathUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        List<Position> newPositionList = new ArrayList<>();
        newPositionList.add(new Position(100, 150));
        newPositionList.add(new Position(300, 350));
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(newLine.getId()));
        input.setPositionList(newPositionList);
        changeLinePathUseCase.execute(input, output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());


        Line resultLine = lineRepository.findById(UUID.fromString(output.getId())).get();
        assertNotNull(resultLine);
        assertEquals(boardId, resultLine.getBoardId());
        assertEquals(100, resultLine.getPositionList().get(0).getX());
        assertEquals(150, resultLine.getPositionList().get(0).getY());
        assertEquals(300, resultLine.getPositionList().get(1).getX());
        assertEquals(350, resultLine.getPositionList().get(1).getY());

    }

}
