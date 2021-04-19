package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.adapter.repository.stickyNote.StickyNoteRepository;
import ntut.csie.islab.miro.entity.stickyNote.StickyNote;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class EditStickyNoteUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public EditStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public EditStickyNoteInput newInput() {
        return new EditStickyNoteInput();
    }

    public void execute(EditStickyNoteInput input, CqrsCommandPresenter output) {
        StickyNote stickyNote = stickyNoteRepository.findById(input.getStickyNoteId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getStickyNoteId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Edit stickyNote failed: stickyNote not found, stickyNote id = " + input.getStickyNoteId());
            return;
        }

        stickyNoteRepository.edit(stickyNote, input.getContent(), input.getStyle());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
    }
}
