package ntut.csie.sslab.kanban.usecase.sticker.create;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface CreateStickerUseCase extends Command<
        CreateStickerInput, CqrsCommandOutput> {
}
