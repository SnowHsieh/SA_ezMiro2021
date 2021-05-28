package ntut.csie.islab.miro.usecase.textfigure.stickynote.changecontent;

import ntut.csie.islab.miro.entity.model.textfigure.stickynote.StickyNote;
import ntut.csie.islab.miro.usecase.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.textfigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeStickyNoteContentUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickyNoteContentUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeStickyNoteContentInput newInput() {
        return new ChangeStickyNoteContentInput();
    }

    public void execute(ChangeStickyNoteContentInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change stickyNoteContent failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNote.changeContent(input.getContent());
        stickyNoteRepository.save((StickyNote) stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Change stickyNoteContent success");
    }

}
