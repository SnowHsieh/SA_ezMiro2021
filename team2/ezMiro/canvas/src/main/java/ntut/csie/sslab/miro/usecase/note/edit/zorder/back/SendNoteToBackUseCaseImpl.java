package ntut.csie.sslab.miro.usecase.note.edit.zorder.back;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class SendNoteToBackUseCaseImpl implements SendNoteToBackUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public SendNoteToBackUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(SendNoteToBackInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null) {
            output.setId(input.getNoteId())
                  .setMessage("Send note to back failed: note not found, note id = " + input.getNoteId());
            return;
        }

        note.sendToBack();

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public SendNoteToBackInput newInput() {
        return new SendNoteToBackInputImpl();
    }

    private class SendNoteToBackInputImpl implements SendNoteToBackInput {
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