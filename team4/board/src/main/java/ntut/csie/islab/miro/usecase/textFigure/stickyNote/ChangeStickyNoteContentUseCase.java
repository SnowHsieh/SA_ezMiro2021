package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeStickyNoteContentUseCase {
    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickyNoteContentUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeStickyNoteContentInput newInput() {
        return new ChangeStickyNoteContentInput();
    }

    public void execute(ChangeStickyNoteContentInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change stickyNoteContent failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
//        stickyNote.changeContent(input.getContent());
        stickyNoteRepository.changeContent(stickyNote, input.getContent());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Change stickyNoteContent success");
    }

}
