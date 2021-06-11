package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure.AttachConnectableFigureInput;
import ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure.AttachConnectablefigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AttachConnectablefigureUseCaseTest extends AbstractSpringBootJpaTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }
    @Test
    public void test_connectable_text_figure_usecase() {

        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(0, 50));
        positionList.add(new Position(100, 150));
        CqrsCommandPresenter newLine = generateCreateLineUseCaseOutput(
                boardId,
                positionList,
                5,
                "#000000"
        );
        CqrsCommandPresenter newStickyNote = generateCreateStickyNoteUseCaseOutput(
                board.getBoardId(),
                new Position(1.0,1.0),
                "Content1",
                new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));


        AttachConnectablefigureUseCase attachConnectablefigureUseCase = new AttachConnectablefigureUseCase(domainEventBus, lineRepository);
        AttachConnectableFigureInput input = attachConnectablefigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(newLine.getId()));
        input.setConnectableFigureId(UUID.fromString(newStickyNote.getId()));
        input.setAttachEndPointKind("destination");

        attachConnectablefigureUseCase.execute(input, output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Line resultLine = lineRepository.findById(UUID.fromString(output.getId())).get();
        assertNotNull(resultLine);
        assertEquals(UUID.fromString(newStickyNote.getId()), resultLine.getDestConnectableFigureId());

    }
}
