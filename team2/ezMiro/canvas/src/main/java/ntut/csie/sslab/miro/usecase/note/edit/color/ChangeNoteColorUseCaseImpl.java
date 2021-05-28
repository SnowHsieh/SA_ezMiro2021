package ntut.csie.sslab.miro.usecase.note.edit.color;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class ChangeNoteColorUseCaseImpl implements ChangeNoteColorUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeNoteColorUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteColorInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null) {
            output.setId(input.getNoteId())
                  .setMessage("Change note color failed: note not found, note id = " + input.getNoteId());
            return;
        }

        note.changeColor(input.getColor());

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public ChangeNoteColorInput newInput() { return new ChangeNoteColorInputImpl();}

    private static class ChangeNoteColorInputImpl implements ChangeNoteColorInput {
        private String noteId;
        private String color;

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

    }
}