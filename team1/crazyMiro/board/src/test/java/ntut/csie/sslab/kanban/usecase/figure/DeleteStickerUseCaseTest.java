package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.delete.DeleteStickerUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.delete.DeleteStickerInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.delete.DeleteStickerUseCase;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteStickerUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    public void delete_a_sticker() {
        String boardId = UUID.randomUUID().toString();
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getSize(), stickerDto.getColor(), stickerDto.getPosition());
        DeleteStickerUseCase deleteStickerUseCase = new DeleteStickerUseCaseImpl(figureRepository, domainEventBus);
        DeleteStickerInput input = deleteStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);

        deleteStickerUseCase.execute(input, output);

        assertTrue(figureRepository.findById(output.getId()).isPresent());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertTrue(sticker.isDeleted());
        assertEquals(stickerId, output.getId());
        assertEquals(2, eventListener.getEventCount());
    }
}
