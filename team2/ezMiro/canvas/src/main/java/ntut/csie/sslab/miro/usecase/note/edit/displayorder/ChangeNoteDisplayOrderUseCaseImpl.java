package ntut.csie.sslab.miro.usecase.note.edit.displayorder;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class ChangeNoteDisplayOrderUseCaseImpl implements ChangeNoteDisplayOrderUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeNoteDisplayOrderUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteDisplayOrderInput input, CqrsCommandOutput output) {
        Note note = (Note)figureRepository.findById(input.getNoteId()).orElse(null);
        // TODO: Type cast need to fix.
        if (note == null) {
            output.setId(input.getNoteId())
                    .setMessage("Change note display order failed: note not found, note id = " + input.getNoteId());
//           domainEventBus.post()
            return;
        }

        note.changeDisplayOrder(input.getDisplayOrder());

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public ChangeNoteDisplayOrderInput newInput() {
        return new ChangeNoteDisplayOrderInputImpl();
    }

    private class ChangeNoteDisplayOrderInputImpl implements ChangeNoteDisplayOrderInput {
        private String noteId;
        private int displayOrder;

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        @Override
        public int getDisplayOrder() {
            return displayOrder;
        }

        @Override
        public void setDisplayOrder(int displayOrder) {
            this.displayOrder = displayOrder;
        }
    }
}