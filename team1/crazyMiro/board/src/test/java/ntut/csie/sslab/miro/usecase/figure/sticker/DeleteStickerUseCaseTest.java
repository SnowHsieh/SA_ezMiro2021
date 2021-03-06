package ntut.csie.sslab.miro.usecase.figure.sticker;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.ConnectionFigureDto;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCase;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteStickerUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    public void delete_a_sticker() {
        String boardId = UUID.randomUUID().toString();
        Coordinate sticker1Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker2Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        ConnectionFigureDto stickerDto1 = new ConnectionFigureDto(null, "sticker1", 10, 10, "black", sticker1Position);
        ConnectionFigureDto stickerDto2 = new ConnectionFigureDto(null, "sticker2", 20, 10, "blue", sticker2Position);
        String stickerId1 = createSticker(boardId, stickerDto1.getContent(), stickerDto1.getWidth(), stickerDto1.getLength(), stickerDto1.getColor(), stickerDto1.getPosition());
        String stickerId2 = createSticker(boardId, stickerDto2.getContent(), stickerDto2.getWidth(), stickerDto2.getLength(), stickerDto2.getColor(), stickerDto2.getPosition());
        int order = 0;
        DeleteStickerUseCase deleteStickerUseCase = new DeleteStickerUseCaseImpl(stickerRepository, domainEventBus);
        DeleteStickerInput input = deleteStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId1);
        input.setBoardId(boardId);

        deleteStickerUseCase.execute(input, output);

        assertFalse(stickerRepository.findById(output.getId()).isPresent());
        assertTrue(stickerRepository.findById(stickerId2).isPresent());
        assertEquals(stickerId1, output.getId());
        assertEquals(3, eventListener.getEventCount());
        Figure sticker = stickerRepository.findById(stickerId2).get();
    }
}
