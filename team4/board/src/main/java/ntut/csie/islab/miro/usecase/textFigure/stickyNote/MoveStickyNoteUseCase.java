package ntut.csie.islab.miro.usecase.textFigure.stickyNote;


import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;

public class MoveStickyNoteUseCase {
    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public MoveStickyNoteUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public MoveStickyNoteInput newInput() {
        return new MoveStickyNoteInput();
    }

    public void execute(MoveStickyNoteInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNote.changePosition(input.getNewPosition());
        stickyNoteRepository.move(stickyNote, input.getNewPosition());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Move stickyNote success");
    }
}
