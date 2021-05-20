package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetStickersUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void get_all_stickers() {

        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "test_board");
        Coordinate sticker1Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sticker2Position = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto1 = new FigureDto(null, "sticker1", 10, 10, "black", sticker1Position);
        FigureDto stickerDto2 = new FigureDto(null, "sticker2", 20, 20, "blue", sticker2Position);
        String stickerId1 = createSticker(boardId, stickerDto1.getContent(), stickerDto1.getWidth(), stickerDto1.getLength(), stickerDto1.getColor(), stickerDto1.getPosition());
        String stickerId2 = createSticker(boardId, stickerDto2.getContent(), stickerDto2.getWidth(), stickerDto2.getLength(), stickerDto2.getColor(), stickerDto2.getPosition());


        List<Figure> stickers = new ArrayList<>();
        Board board = boardRepository.findById(boardId).get();
        List<CommittedFigure> committedFigures = board.getCommittedFigures();
        committedFigures.forEach(x-> stickers.add(figureRepository.findById(x.getFigureId()).get()));

        List<FigureDto> stickerDtos = ConvertStickerToDto.transform(stickers);

        assertEquals(2, stickerDtos.size());
        assertEquals(stickerId1, stickerDtos.get(0).getFigureId());
        assertEquals(stickerDto1.getContent(), stickerDtos.get(0).getContent());
        assertEquals(stickerDto1.getWidth(), stickerDtos.get(0).getWidth());
        assertEquals(stickerDto1.getLength(), stickerDtos.get(0).getLength());
        assertEquals(stickerDto1.getColor(), stickerDtos.get(0).getColor());
        assertTrue(stickerDto1.getPosition().equals(stickerDtos.get(0).getPosition()));
        assertEquals(stickerId2, stickerDtos.get(1).getFigureId());
        assertEquals(stickerDto2.getContent(), stickerDtos.get(1).getContent());
        assertEquals(stickerDto2.getWidth(), stickerDtos.get(1).getWidth());
        assertEquals(stickerDto2.getLength(), stickerDtos.get(1).getLength());
        assertEquals(stickerDto2.getColor(), stickerDtos.get(1).getColor());
        assertTrue(stickerDto2.getPosition().equals(stickerDtos.get(1).getPosition()));
    }


}
