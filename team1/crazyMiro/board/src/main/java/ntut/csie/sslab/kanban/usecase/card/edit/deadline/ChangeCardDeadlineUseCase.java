package ntut.csie.sslab.kanban.usecase.card.edit.deadline;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface ChangeCardDeadlineUseCase extends Command<ChangeCardDeadlineInput, CqrsCommandOutput> {
}
