package ntut.csie.team5.usecase.figure.connectable_figure.note.change_color;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

public class ChangeNoteColorUseCaseImpl implements ChangeNoteColorUseCase {

    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public ChangeNoteColorUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ChangeNoteColorInput newInput() {
        return new ChangeNoteColorInputImpl();
    }

    @Override
    public void execute(ChangeNoteColorInput input, CqrsCommandOutput output) {
        Note note = noteRepository.findById(input.getFigureId()).orElse(null);

        if(null == note)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change note color failed: note not found, note id = " + input.getFigureId());
            return;
        }

        note.changeColor(input.getColor());
        noteRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ChangeNoteColorInputImpl implements ChangeNoteColorInput {
        String figureId;
        String color;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
            this.color = color;
        }
    }
}
