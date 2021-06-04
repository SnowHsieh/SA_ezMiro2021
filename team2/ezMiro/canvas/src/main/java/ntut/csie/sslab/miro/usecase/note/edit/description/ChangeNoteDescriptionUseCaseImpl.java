package ntut.csie.sslab.miro.usecase.note.edit.description;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class ChangeNoteDescriptionUseCaseImpl implements ChangeNoteDescriptionUseCase{

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeNoteDescriptionUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteDescriptionInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null) {
           output.setId(input.getNoteId())
                 .setMessage("Change note description failed: note not found, note id = " + input.getNoteId());
           return;
        }

        note.changeDescription(input.getDescription());

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public ChangeNoteDescriptionInput newInput() {
        return new ChangeNoteDescriptionInputImpl();
    }

    private static class ChangeNoteDescriptionInputImpl implements ChangeNoteDescriptionInput {
        private String noteId;
        private String description;

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}