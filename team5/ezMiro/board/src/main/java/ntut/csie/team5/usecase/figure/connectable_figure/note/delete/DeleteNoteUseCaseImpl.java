package ntut.csie.team5.usecase.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

public class DeleteNoteUseCaseImpl implements DeleteNoteUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public DeleteNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DeleteNoteInput newInput() {
        return new DeleteNoteUseCaseImpl.DeleteNoteInputImpl();
    }

    @Override
    public void execute(DeleteNoteInput input, CqrsCommandOutput output) {
        Figure note = figureRepository.findById(input.getFigureId()).orElse(null);

        if(null == note)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete note failed: note not found, note id = " + input.getFigureId());
            return;
        }

        note.markAsRemoved();

        figureRepository.deleteById(note.getId());
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class DeleteNoteInputImpl implements DeleteNoteInput {
        
        private String noteId;

        @Override
        public String getFigureId() {
            return noteId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.noteId = figureId;
        }
    }
}
