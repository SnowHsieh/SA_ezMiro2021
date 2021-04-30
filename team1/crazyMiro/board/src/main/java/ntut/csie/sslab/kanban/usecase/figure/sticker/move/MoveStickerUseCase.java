package ntut.csie.sslab.kanban.usecase.figure.sticker.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface MoveStickerUseCase extends Command<MoveStickerInput, CqrsCommandOutput> {
}
