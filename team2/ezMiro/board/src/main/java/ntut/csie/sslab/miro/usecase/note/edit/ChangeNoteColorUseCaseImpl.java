package ntut.csie.sslab.miro.usecase.note.edit;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.NoteRepository;

public class ChangeNoteColorUseCaseImpl implements ChangeNoteColorUseCase {

    public NoteRepository noteRepository;
    public DomainEventBus domainEventBus;

    public ChangeNoteColorUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteColorInput input, CqrsCommandOutput output) {
        Note note = noteRepository.findById(input.getNoteId()).orElse(null);
        if (note == null){
            output.setId(input.getNoteId())
                    .setMessage("Change note color failed: note not found, note id = " + input.getNoteId());
//           domainEventBus.post()
            return;
        }

        note.changeColor(input.getNewColor());

        noteRepository.save(note);

        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public ChangeNoteColorInput newInput() { return new ChangeNoteColorInputImpl();}

    private static class ChangeNoteColorInputImpl implements ChangeNoteColorInput {
        private String noteId;
        private String color;
        private String boardId;

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        public String getNewColor() {
            return color;
        }

        public void setNewColor(String color) {
            this.color = color;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
