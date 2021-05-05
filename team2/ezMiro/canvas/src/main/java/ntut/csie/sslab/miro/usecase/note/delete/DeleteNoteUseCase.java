package ntut.csie.sslab.miro.usecase.note.delete;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface DeleteNoteUseCase extends Command<DeleteNoteInput, CqrsCommandOutput> {
}
