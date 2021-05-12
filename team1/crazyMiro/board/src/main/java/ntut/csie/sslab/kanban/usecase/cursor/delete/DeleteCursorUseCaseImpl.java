package ntut.csie.sslab.kanban.usecase.cursor.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

public class DeleteCursorUseCaseImpl implements DeleteCursorUseCase {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public DeleteCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(DeleteCursorInput input, CqrsCommandOutput output) {

        Cursor cursor = cursorRepository.getCursorBySessionId(input.getSessionId()).get();

        cursor.deleteCursor();
        cursorRepository.deleteById(input.getSessionId());
        domainEventBus.postAll(cursor);

        output.setId(cursor.getId())
                .setExitCode(ExitCode.SUCCESS);

    }

    @Override
    public DeleteCursorInput newInput() {
        return new DeleteCursorInputImpl();
    }

    private class DeleteCursorInputImpl implements DeleteCursorInput {
        private String sessionId;

        @Override
        public String getSessionId() {
            return sessionId;
        }

        @Override
        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
