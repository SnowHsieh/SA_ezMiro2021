package ntut.csie.sslab.kanban.usecase.workspace;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.workspace.Coordinate;
import ntut.csie.sslab.kanban.entity.model.workspace.Sticker;
import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.sticker.create.CreateStickerInput;
import ntut.csie.sslab.kanban.usecase.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.kanban.usecase.sticker.create.CreateStickerUseCaseImpl;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class CreateStickerUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_sticker(){
        String workspaceId = createWorkspace();
        String content = "stickerIsCreated";
        int size = 10;
        String color = "black";
        long x = new Random().nextLong();
        long y = new Random().nextLong();
        Coordinate position = new Coordinate(x, y);
        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(workspaceRepository);
        CreateStickerInput input = createStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setWorkspaceId(workspaceId);
        input.setContent(content);
        input.setSize(size);
        input.setColor(color);
        input.setPosition(position);

        createStickerUseCase.execute(input, output);

        assertNotNull(output.getId());
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        assertTrue(workspace.getFigureById(output.getId()).isPresent());
        Sticker sticker = (Sticker) workspace.getFigureById(output.getId()).get();
        assertEquals(content, sticker.getContent());
        assertEquals(size, sticker.getSize());
        assertEquals(color, sticker.getColor());
        assertEquals(x, sticker.getPosition().getX());
        assertEquals(y, sticker.getPosition().getY());
    }
}
