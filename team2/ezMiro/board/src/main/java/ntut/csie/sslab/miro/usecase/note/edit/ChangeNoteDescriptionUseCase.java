package ntut.csie.sslab.miro.usecase.note.edit;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface ChangeNoteDescriptionUseCase extends Command<ChangeNoteDescriptionInput, CqrsCommandOutput> {
}
