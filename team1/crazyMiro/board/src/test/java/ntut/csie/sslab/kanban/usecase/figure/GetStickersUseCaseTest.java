package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStickersUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void get_all_stickers() {

        String boardId = UUID.randomUUID().toString();
        Coordinate sticker1Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker2Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto1 = new FigureDto(null, "sticker1", 10, "black", sticker1Position);
        FigureDto stickerDto2 = new FigureDto(null, "sticker2", 20, "blue", sticker2Position);
        String stickerId1 = createSticker(boardId, stickerDto1.getContent(), stickerDto1.getSize(), stickerDto1.getColor(), stickerDto1.getPosition());
        String stickerId2 = createSticker(boardId, stickerDto2.getContent(), stickerDto2.getSize(), stickerDto2.getColor(), stickerDto2.getPosition());

        List<Figure> stickers = figureRepository.getStickersByBoardId(boardId);
        List<FigureDto> stickerDtos = ConvertStickerToDto.transform(stickers);

        assertEquals(2, stickerDtos.size());
        assertEquals(stickerId1, stickerDtos.get(0).getFigureId());
        assertEquals(stickerDto1.getContent(), stickerDtos.get(0).getContent());
        assertEquals(stickerDto1.getSize(), stickerDtos.get(0).getSize());
        assertEquals(stickerDto1.getColor(), stickerDtos.get(0).getColor());
        assertEquals(stickerDto1.getPosition(), stickerDtos.get(0).getPosition());
        assertEquals(stickerId2, stickerDtos.get(1).getFigureId());
        assertEquals(stickerDto2.getContent(), stickerDtos.get(1).getContent());
        assertEquals(stickerDto2.getSize(), stickerDtos.get(1).getSize());
        assertEquals(stickerDto2.getColor(), stickerDtos.get(1).getColor());
        assertEquals(stickerDto2.getPosition(), stickerDtos.get(1).getPosition());
    }

    private String createSticker(String boardId, String content, int size, String color, Coordinate position) {
        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(figureRepository, domainEventBus);
        CreateStickerInput input = createStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setContent(content);
        input.setSize(size);
        input.setColor(color);
        input.setPosition(position);

        createStickerUseCase.execute(input, output);

        return output.getId();
    }
}
