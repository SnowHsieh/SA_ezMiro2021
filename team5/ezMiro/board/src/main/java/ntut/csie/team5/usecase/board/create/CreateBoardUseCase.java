package ntut.csie.team5.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;

public interface CreateBoardUseCase extends Command<CreateBoardInput, CqrsCommandOutput> {

}
