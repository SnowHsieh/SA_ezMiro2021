package ntut.csie.sslab.miro.usecase.note.create;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface CreateNoteUseCase extends Command<CreateNoteInput, CqrsCommandOutput> {

}
