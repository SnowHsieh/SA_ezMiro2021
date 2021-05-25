package ntut.csie.sslab.miro.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeStickerSizeUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void change_sticker_size() {
        String boardId = UUID.randomUUID().toString();
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getWidth(), stickerDto.getLength(), stickerDto.getColor(), stickerDto.getPosition());
        int newWidth = new Random().nextInt();
        int newLength = new Random().nextInt();
        ChangeStickerSizeUseCase changeStickerSizeUseCase = new ChangeStickerSizeUseCaseImpl(figureRepository, domainEventBus);
        ChangeStickerSizeInput input = changeStickerSizeUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);
        input.setWidth(newWidth);
        input.setLength(newLength);

        changeStickerSizeUseCase.execute(input, output);

        assertTrue(figureRepository.findById(output.getId()).isPresent());
        assertEquals(input.getFigureId(), output.getId());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertEquals(newWidth, sticker.getWidth());
        assertEquals(newLength, sticker.getLength());
        assertEquals(2, eventListener.getEventCount());

        changeStickerSizeUseCase.execute(input, output);
        assertEquals(2, eventListener.getEventCount());
    }

}
