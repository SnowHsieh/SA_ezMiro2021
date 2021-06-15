package ntut.csie.team5.usecase.figure.connectable_figure.note.resize;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

public class ResizeNoteUseCaseImpl implements ResizeNoteUseCase {

    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public ResizeNoteUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ResizeNoteInput newInput() {
        return new ResizeNoteInputImpl();
    }

    @Override
    public void execute(ResizeNoteInput input, CqrsCommandOutput output) {
        Note note = noteRepository.findById(input.getFigureId()).orElse(null);

        if(null == note)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Resize note failed: note not found, note id = " + input.getFigureId());
            return;
        }

        note.changeSize(input.getHeight(), input.getWidth());
        noteRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ResizeNoteInputImpl implements ResizeNoteInput {

        private String figureId;
        private int height;
        private int width;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setWidth(int width) {
            this.width = width;
        }
    }
}
