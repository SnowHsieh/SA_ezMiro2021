package ntut.csie.sslab.miro.usecase.note.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class MoveNoteUseCaseImpl implements MoveNoteUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public MoveNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveNoteInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null){
            output.setId(input.getNoteId())
                  .setMessage("Move note failed: note not found, note id = " + input.getNoteId());
            return;
        }

        note.move(input.getCoordinate());

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public MoveNoteInput newInput() {
        return new MoveNoteInputImpl();
    }

    private class MoveNoteInputImpl implements MoveNoteInput {
        private String noteId;
        private Coordinate coordinate;

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public Coordinate getCoordinate() {
            return coordinate;
        }
    }
}