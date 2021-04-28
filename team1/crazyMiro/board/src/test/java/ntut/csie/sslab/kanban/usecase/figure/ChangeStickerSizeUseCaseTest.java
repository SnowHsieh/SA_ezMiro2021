package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeUseCaseImpl;
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
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getSize(), stickerDto.getColor(), stickerDto.getPosition());
        int newSize = new Random().nextInt();
        ChangeStickerSizeUseCase changeStickerSizeUseCase = new ChangeStickerSizeUseCaseImpl(figureRepository, domainEventBus);
        ChangeStickerSizeInput input = changeStickerSizeUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);
        input.setSize(newSize);

        changeStickerSizeUseCase.execute(input, output);

        assertEquals(input.getFigureId(), output.getId());
        assertTrue(figureRepository.findById(output.getId()).isPresent());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertEquals(newSize, sticker.getSize());
        assertEquals(2, eventListener.getEventCount());
    }

}
