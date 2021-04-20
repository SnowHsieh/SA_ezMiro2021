package ntut.csie.sslab.kanban.usecase.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.ddd.usecase.AbstractRepository;

import java.util.List;

public interface WorkflowRepository extends AbstractRepository<Workflow, String> {

    List<Workflow> getWorkflowsByBoardId(String boardId);

}
