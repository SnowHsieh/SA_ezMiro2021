package ntut.csie.team5.usecase.board.move_cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;

public interface MoveCursorUseCase extends Command<MoveCursorInput, CqrsCommandPresenter> {
}
