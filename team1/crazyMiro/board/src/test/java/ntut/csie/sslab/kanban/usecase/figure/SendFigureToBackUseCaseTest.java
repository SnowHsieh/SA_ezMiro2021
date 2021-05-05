package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.figure.sendtoback.SendFigureToBackInput;
import ntut.csie.sslab.kanban.usecase.figure.sendtoback.SendFigureToBackUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sendtoback.SendFigureToBackUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendFigureToBackUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    public void send_a_sticker_to_back(){
        String boardId1 = UUID.randomUUID().toString();
        String boardId2 = UUID.randomUUID().toString();
        Coordinate sticker1Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker2Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker3Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker4Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker5Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto1 = new FigureDto(null, "sticker1", 10, "black", sticker1Position);
        FigureDto stickerDto2 = new FigureDto(null, "sticker2", 20, "blue", sticker2Position);
        FigureDto stickerDto3 = new FigureDto(null, "sticker3", 20, "white", sticker3Position);
        FigureDto stickerDto4 = new FigureDto(null, "sticker4", 20, "green", sticker4Position);
        FigureDto stickerDto5 = new FigureDto(null, "sticker5", 20, "red", sticker5Position);
        String stickerId1 = createSticker(boardId1, stickerDto1.getContent(), stickerDto1.getSize(), stickerDto1.getColor(), stickerDto1.getPosition());
        String stickerId2 = createSticker(boardId1, stickerDto2.getContent(), stickerDto2.getSize(), stickerDto2.getColor(), stickerDto2.getPosition());
        String stickerId3 = createSticker(boardId1, stickerDto3.getContent(), stickerDto3.getSize(), stickerDto3.getColor(), stickerDto3.getPosition());
        String stickerId4 = createSticker(boardId2, stickerDto4.getContent(), stickerDto4.getSize(), stickerDto4.getColor(), stickerDto4.getPosition());
        String stickerId5 = createSticker(boardId2, stickerDto5.getContent(), stickerDto5.getSize(), stickerDto5.getColor(), stickerDto5.getPosition());
        SendFigureToBackUseCase sendFigureToBackUseCase = new SendFigureToBackUseCaseImpl(figureRepository, domainEventBus);
        SendFigureToBackInput input = sendFigureToBackUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId1);
        input.setFigureId(stickerId2);

        sendFigureToBackUseCase.execute(input, output);

        List<Figure> board1Stickers = figureRepository.getStickersByBoardId(boardId1);
        List<Figure> board2Stickers = figureRepository.getStickersByBoardId(boardId2);
        assertEquals(stickerId2, board1Stickers.get(0).getFigureId());
        assertEquals(0, board1Stickers.get(0).getOrder());
        assertEquals(stickerId1, board1Stickers.get(1).getFigureId());
        assertEquals(1, board1Stickers.get(1).getOrder());
        assertEquals(stickerId3, board1Stickers.get(2).getFigureId());
        assertEquals(2, board1Stickers.get(2).getOrder());
        assertEquals(stickerId4, board2Stickers.get(0).getFigureId());
        assertEquals(0, board1Stickers.get(0).getOrder());
        assertEquals(stickerId5, board2Stickers.get(1).getFigureId());
        assertEquals(1, board1Stickers.get(1).getOrder());
        assertEquals(6, eventListener.getEventCount());
    }

}
