package ntut.csie.sslab.miro.usecase.note.edit.description;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.NoteRepository;

public class ChangeNoteDescriptionUseCaseImpl implements ChangeNoteDescriptionUseCase{

    public NoteRepository noteRepository;
    public DomainEventBus domainEventBus;

    public ChangeNoteDescriptionUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteDescriptionInput input, CqrsCommandOutput output) {
        Note note = noteRepository.findById(input.getNoteId()).orElse(null);
        if (note == null){
           output.setId(input.getNoteId())
                   .setMessage("Change note description failed: note not found, note id = " + input.getNoteId());
//           domainEventBus.post()
            return;
        }

        note.changeDescription(input.getNewDescription(), input.getBoardId());

        noteRepository.save(note);
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
        private String boardId;

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setNewDescription(String description) {
            this.description = description;
        }

        @Override
        public String getNewDescription() {
            return description;
        }
    }
}
