package ntut.csie.sslab.miro.usecase.cursor.remove;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;

public class RemoveCursorUseCaseImpl implements RemoveCursorUseCase {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public RemoveCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
        this.cursorRepository = cursorRepository;
    }

    @Override
    public void execute(RemoveCursorInput input, CqrsCommandOutput output) {
        Cursor cursor = cursorRepository.findById(input.getCursorId()).orElse(null);
        if (cursor == null) {
            output.setId(input.getCursorId())
                    .setMessage("Remove cursor failed: cursor not found, cursor id = " + input.getCursorId());
            return;
        }

        cursor.markAsRemoved();
        cursorRepository.deleteById(cursor.getId());

        domainEventBus.postAll(cursor);

        output.setId(cursor.getId());
    }

    @Override
    public RemoveCursorInput newInput() {
        return new RemoveCursorInputImpl();
    }

    private class RemoveCursorInputImpl implements RemoveCursorInput {
        private String cursorId;

        @Override
        public String getCursorId() {
            return cursorId;
        }

        @Override
        public void setCursorId(String cursorId) {
            this.cursorId = cursorId;
        }
    }
}