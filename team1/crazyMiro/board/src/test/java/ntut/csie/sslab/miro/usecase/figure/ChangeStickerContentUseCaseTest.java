package ntut.csie.sslab.miro.usecase.figure;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeStickerContentUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void change_sticker_content () {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "test_board");
        eventListener.clearEventCount();
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        FigureDto stickerDto = new FigureDto(null, "sticker1", 10, 10, "black", stickerPosition);
        String stickerId = createSticker(boardId, stickerDto.getContent(), stickerDto.getWidth(), stickerDto.getLength(), stickerDto.getColor(), stickerDto.getPosition());
        String newContent = "newContent";
        ChangeStickerContentUseCase changeStickerContentUseCase = new ChangeStickerContentUseCaseImpl(figureRepository, domainEventBus);
        ChangeStickerContentInput input = changeStickerContentUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);
        input.setContent(newContent);

        changeStickerContentUseCase.execute(input, output);

        assertTrue(figureRepository.findById(output.getId()).isPresent());
        assertEquals(input.getFigureId(), output.getId());
        Figure sticker = figureRepository.findById(output.getId()).get();
        assertEquals(newContent, sticker.getContent());
        assertEquals(3, eventListener.getEventCount());

        changeStickerContentUseCase.execute(input, output);
        assertEquals(3, eventListener.getEventCount());
    }
}
