package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeStickerColorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void change_sticker_color() {
        String boardId = UUID.randomUUID().toString();
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getSize(), stickerDto.getColor(), stickerDto.getPosition());
        String newColor = "yellow";
        ChangeStickerColorUseCase changeStickerColorUseCase = new ChangeStickerColorUseCaseImpl(figureRepository, domainEventBus);
        ChangeStickerColorInput input = changeStickerColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);
        input.setColor(newColor);

        changeStickerColorUseCase.execute(input, output);

        assertTrue(figureRepository.findById(output.getId()).isPresent());
        assertEquals(input.getFigureId(), output.getId());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertEquals(newColor, sticker.getColor());
        assertEquals(2, eventListener.getEventCount());
    }

}
