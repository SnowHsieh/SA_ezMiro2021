package ntut.csie.sslab.miro.usecase.note.edit.zorder.front;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class BringNoteToFrontUseCaseImpl implements BringNoteToFrontUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public BringNoteToFrontUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(BringNoteToFrontInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null) {
            output.setId(input.getNoteId())
                    .setMessage("Bring note to front failed: note not found, note id = " + input.getNoteId());
            return;
        }

        note.bringToFront();

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public BringNoteToFrontInput newInput() {
        return new BringNoteToFrontInputImpl();
    }

    private class BringNoteToFrontInputImpl implements BringNoteToFrontInput {
        private String noteId;

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }
    }
}