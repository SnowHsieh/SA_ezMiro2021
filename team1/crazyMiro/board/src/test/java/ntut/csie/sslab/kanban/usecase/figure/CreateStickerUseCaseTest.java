package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.FigureType;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateStickerUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_sticker(){
        String boardId = UUID.randomUUID().toString();
        String content = "stickerIsCreated";
        int size = 10;
        String color = "black";
        long x = new Random().nextLong();
        long y = new Random().nextLong();
        Coordinate position = new Coordinate(x, y);
        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(figureRepository, domainEventBus);
        CreateStickerInput input = createStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setContent(content);
        input.setSize(size);
        input.setColor(color);
        input.setPosition(position);

        createStickerUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertTrue(figureRepository.findById(output.getId()).isPresent());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertEquals(content, sticker.getContent());
        assertEquals(size, sticker.getSize());
        assertEquals(color, sticker.getColor());
        assertTrue(position.equals(sticker.getPosition()));
        assertEquals(FigureType.Sticker, sticker.getType());
        assertEquals(1, eventListener.getEventCount());
    }
}
