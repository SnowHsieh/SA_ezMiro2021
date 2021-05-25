package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeStickyNoteColorUseCase {
    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickyNoteColorUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeStickyNoteColorInput newInput() {
        return new ChangeStickyNoteColorInput();
    }

    public void execute(ChangeStickyNoteColorInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);
        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("change stickyNote color failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNoteRepository.changeColor(stickyNote, input.getColor());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("change stickyNote color success");

    }
}
