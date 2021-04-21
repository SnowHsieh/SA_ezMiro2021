//package ntut.csie.sslab.kanban.usecase.workspace;
//
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.kanban.entity.model.workspace.Coordinate;
//import ntut.csie.sslab.kanban.usecase.workspace.create.sticker.CreateStickerInput;
//import ntut.csie.sslab.kanban.usecase.workspace.create.sticker.CreateStickerUseCase;
//import ntut.csie.sslab.kanban.usecase.workspace.create.sticker.CreateStickerUseCaseImpl;
//import org.junit.jupiter.api.Test;
//
//import java.util.Random;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//public class CreateStickerUseCaseTest {
//    private WorkspaceRepository workspaceRepository;
//
//
//
//
//    @Test
//    public void create_a_sticker(){
//        String workspaceId = UUID.randomUUID().toString();
//        String content = "stickerIsCreated";
//        int size = 10;
//        String color = "black";
//        long x = new Random().nextLong();
//        long y = new Random().nextLong();
//        Coordinate position = new Coordinate(x, y);
//        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(workSpaceRepository);
//        CreateStickerInput input = createStickerUseCase.newInput();
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        input.setWorkspaceId(workspaceId);
//        input.setContent(content);
//        input.setSize(size);
//        input.setColor(color);
//        input.setPosition(position);
//
//        createStickerUseCase.execute(input, output);
//
//        assertEquals(workspaceId, output.getId());
//        assertTrue(workSpaceRepository.getFigureById(stickerId).isPresent());
//        Sticker sticker = (Sticker) workSpaceRepository.getFigureById(stickerId).get();
//        assertEquals(stickerId, sticker.getStickerId());
//        assertEquals(workspaceId, sticker.getWorkspaceId());
//        assertEquals(content, sticker.getContent());
//        assertEquals(size, sticker.getSize());
//        assertEquals(color, sticker.getColor());
//        assertEquals(position, sticker.getPosition());
//
//
//
//
//    }
//}
