package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.entity.model.figure.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureInput;
import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureUseCase;
import ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure.UnattachTextfigureInput;
import ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure.UnattachTextfigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnattachTextfigureUseCaseTest extends AbstractSpringBootJpaTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }
    @Test
    public void test_unattach_text_figure_usecase() {

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

        CqrsCommandPresenter attachTextfigureOutput =  generateAttachTextfigureUseCase(
                boardId,
                UUID.fromString(newLine.getId()),
                UUID.fromString(newStickyNote.getId()),
                "destination"
        );

        assertNotNull(attachTextfigureOutput.getId());
        assertEquals(ExitCode.SUCCESS, attachTextfigureOutput.getExitCode());
        Line attachedLine = lineRepository.findById(UUID.fromString(attachTextfigureOutput.getId())).get();
        assertNotNull(attachedLine);
        assertEquals(UUID.fromString(newStickyNote.getId()), attachedLine.getDestTextFigureId());

        UnattachTextfigureUseCase unattachTextfigureUseCase = new UnattachTextfigureUseCase(domainEventBus, lineRepository);
        UnattachTextfigureInput input = unattachTextfigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(newLine.getId()));
        input.setAttachEndPointKind("destination");

        unattachTextfigureUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        Line resultLine = lineRepository.findById(UUID.fromString(output.getId())).get();
        assertNotNull(resultLine);
        assertEquals(null, resultLine.getDestTextFigureId());

    }
}
