package ntut.csie.islab.miro.usecase.figure.stickyNote;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import ntut.csie.islab.miro.entity.figure.FigureShapeKindEnum;
import ntut.csie.islab.miro.entity.figure.FigureStyle;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

import java.util.UUID;

public class CreateStickyNoteUseCaseTest {

    @Test
    public void test_create_sticky_note(){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase();
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(UUID.randomUUID());
        input.setPosition(1.0,1.0);
        input.setContent("Content");
        input.setStyle(new FigureStyle(12, FigureShapeKindEnum.CIRCLE, 87.87, "#948700"));
        createStickyNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
    }

}