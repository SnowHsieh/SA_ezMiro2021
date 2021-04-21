package ntut.csie.sslab.kanban.usecase.workspace;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.workspace.create.CreateWorkspaceInput;
import ntut.csie.sslab.kanban.usecase.workspace.create.CreateWorkspaceUseCase;
import ntut.csie.sslab.kanban.usecase.workspace.create.CreateWorkspaceUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateWorkspaceUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    public void create_a_workspace() {
        String boardId = UUID.randomUUID().toString();
        CreateWorkspaceUseCase createWorkspaceUseCase = new CreateWorkspaceUseCaseImpl(workspaceRepository, domainEventBus);
        CreateWorkspaceInput input = createWorkspaceUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);

        createWorkspaceUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertTrue(workspaceRepository.findById(output.getId()).isPresent());
        Workspace workspace = workspaceRepository.findById(output.getId()).get();
        assertEquals(boardId, workspace.getBoardId());
        assertNotNull(workspace.getAllFigures());
        assertEquals(1, eventListener.getEventCount());
    }
}
