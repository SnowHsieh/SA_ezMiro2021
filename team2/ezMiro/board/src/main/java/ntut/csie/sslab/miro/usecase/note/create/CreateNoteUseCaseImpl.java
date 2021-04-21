package ntut.csie.sslab.miro.usecase.note.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.entity.model.note.NoteBuilder;
import ntut.csie.sslab.miro.usecase.note.NoteRepository;

public class CreateNoteUseCaseImpl implements CreateNoteUseCase {
    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public CreateNoteUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateNoteInput input, CqrsCommandOutput output) {
        Note note = NoteBuilder.newInstance()
                .boardId(input.getBoardId())
                .description("")
                .color("#6FB7B7")
                .build();

        noteRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public CreateNoteInput newInput() {
        return new CreateNoteInputImpl();
    }

    private static class CreateNoteInputImpl implements CreateNoteInput {
        private String boardId;
        private String description;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
