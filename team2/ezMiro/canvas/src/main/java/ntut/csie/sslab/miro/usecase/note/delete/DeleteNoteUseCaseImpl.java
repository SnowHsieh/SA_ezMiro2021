package ntut.csie.sslab.miro.usecase.note.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class DeleteNoteUseCaseImpl implements DeleteNoteUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public DeleteNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(DeleteNoteInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null){
            output.setId(input.getNoteId())
                  .setMessage("Move note failed: note not found, note id = " + input.getNoteId());
            return;
        }
        note.markAsRemoved(input.getBoardId());
        figureRepository.deleteById(input.getNoteId());
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public DeleteNoteInput newInput() {
        return new DeleteNoteInputImpl();
    }

    private class DeleteNoteInputImpl implements DeleteNoteInput {
        private String noteId;
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
    }
}