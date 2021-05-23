package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.board.sendFigureToBack.SendFigureToBackInput;
import ntut.csie.sslab.miro.usecase.board.sendFigureToBack.SendFigureToBackUseCase;
import ntut.csie.sslab.miro.usecase.board.sendFigureToBack.SendFigureToBackUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.FigureDto;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendFigureToBackUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void send_a_sticker_to_back(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate sticker1Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker2Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker3Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto1 = new FigureDto(null, "sticker1", 10, 10, "black", sticker1Position);
        FigureDto stickerDto2 = new FigureDto(null, "sticker2", 20, 20, "blue", sticker2Position);
        FigureDto stickerDto3 = new FigureDto(null, "sticker3", 30, 30, "blue", sticker3Position);
        String stickerId1 = createSticker(boardId, stickerDto1.getContent(), stickerDto1.getWidth(), stickerDto1.getLength(), stickerDto1.getColor(), stickerDto1.getPosition());
        String stickerId2 = createSticker(boardId, stickerDto2.getContent(), stickerDto2.getWidth(), stickerDto2.getLength(), stickerDto2.getColor(), stickerDto2.getPosition());
        String stickerId3 = createSticker(boardId, stickerDto3.getContent(), stickerDto3.getWidth(), stickerDto3.getLength(), stickerDto3.getColor(), stickerDto3.getPosition());
        eventListener.clearEventCount();
        SendFigureToBackUseCase sendFigureToBackUseCase = new SendFigureToBackUseCaseImpl(boardRepository, domainEventBus);
        SendFigureToBackInput input = sendFigureToBackUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(stickerId3);

        sendFigureToBackUseCase.execute(input, output);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(3, board.getCommittedFigures().size());
        assertEquals(stickerId3, board.getCommittedFigures().get(0).getFigureId());
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(stickerId1, board.getCommittedFigures().get(1).getFigureId());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
        assertEquals(stickerId2, board.getCommittedFigures().get(2).getFigureId());
        assertEquals(2, board.getCommittedFigures().get(2).getZOrder());
        assertEquals(1, eventListener.getEventCount());
    }
}
