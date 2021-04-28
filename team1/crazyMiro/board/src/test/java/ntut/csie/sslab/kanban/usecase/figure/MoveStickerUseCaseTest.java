package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.move.MoveStickerInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.move.MoveStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.move.MoveStickerUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveStickerUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void move_a_sticker() {
        String boardId = UUID.randomUUID().toString();
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getSize(), stickerDto.getColor(), stickerDto.getPosition());
        Coordinate newPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        MoveStickerUseCase moveStickerUseCase = new MoveStickerUseCaseImpl(figureRepository, domainEventBus);
        MoveStickerInput input = moveStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);
        input.setPosition(newPosition);

        moveStickerUseCase.execute(input, output);

        assertEquals(input.getFigureId(), output.getId());
        assertTrue(figureRepository.findById(output.getId()).isPresent());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertTrue(newPosition.equals(sticker.getPosition()));
        assertEquals(2, eventListener.getEventCount());
    }
}
