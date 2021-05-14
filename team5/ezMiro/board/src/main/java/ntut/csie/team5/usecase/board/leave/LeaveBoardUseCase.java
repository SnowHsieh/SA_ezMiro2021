package ntut.csie.team5.usecase.board.leave;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;

public interface LeaveBoardUseCase extends Command<LeaveBoardInput, CqrsCommandPresenter> {
}
