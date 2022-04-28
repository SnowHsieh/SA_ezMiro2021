package ntut.csie.islab.miro.usecase.figure.line;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.line.changecolor.ChangeLineColorInput;
import ntut.csie.islab.miro.usecase.figure.line.changecolor.ChangeLineColorUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeLineColorUsecaseTest extends AbstractSpringBootJpaTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
    }
    @Test
    public void test_change_line_color_usecase() {
        ChangeLineColorUseCase changeLineColorUseCase = new ChangeLineColorUseCase(lineRepository,  domainEventBus);
        ChangeLineColorInput input = changeLineColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(preGeneratedLine.getId()));
        input.setColor("#010100");
        changeLineColorUseCase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals("#010100",lineRepository.findById(UUID.fromString(output.getId())).get().getColor());

    }
}
