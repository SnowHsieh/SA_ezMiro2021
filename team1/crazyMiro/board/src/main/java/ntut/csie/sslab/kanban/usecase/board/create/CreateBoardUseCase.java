package ntut.csie.sslab.kanban.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface CreateBoardUseCase extends Command<CreateBoardInput, CqrsCommandOutput> {
}
