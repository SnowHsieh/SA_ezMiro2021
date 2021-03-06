package ntut.csie.sslab.miro.usecase.figure.sticker;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateStickerUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_sticker(){
        String boardId = UUID.randomUUID().toString();
        String content = "stickerIsCreated";
        int length = 10;
        int width = 10;
        String color = "black";
        long x = new Random().nextLong();
        long y = new Random().nextLong();
        Coordinate position = new Coordinate(x, y);
        int expectedOrder = 0;
        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(stickerRepository, domainEventBus);
        CreateStickerInput input = createStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setContent(content);
        input.setLength(length);
        input.setWidth(width);
        input.setColor(color);
        input.setPosition(position);

        createStickerUseCase.execute(input, output);

        assertTrue(stickerRepository.findById(output.getId()).isPresent());
        assertNotNull(output.getId());
        ConnectionFigure sticker = stickerRepository.findById(output.getId()).get();
        assertEquals(content, sticker.getContent());
        assertEquals(length, sticker.getLength());
        assertEquals(width, sticker.getWidth());
        assertEquals(color, sticker.getColor());
        assertTrue(position.equals(sticker.getPosition()));
        assertEquals(FigureType.Sticker, sticker.getType());
        assertEquals(1, eventListener.getEventCount());
    }
}
