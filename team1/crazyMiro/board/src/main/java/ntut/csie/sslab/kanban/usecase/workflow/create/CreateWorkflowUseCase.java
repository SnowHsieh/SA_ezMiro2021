package ntut.csie.sslab.kanban.usecase.workflow.create;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface CreateWorkflowUseCase extends Command<CreateWorkflowInput, CqrsCommandOutput> {
}
