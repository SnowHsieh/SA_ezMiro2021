package ntut.csie.sslab.miro.usecase.workflow;

import ntut.csie.sslab.miro.entity.model.workflow.Workflow;
import ntut.csie.sslab.ddd.usecase.AbstractRepository;

import java.util.List;

public interface WorkflowRepository extends AbstractRepository<Workflow, String> {

    List<Workflow> getWorkflowsByBoardId(String boardId);

}
