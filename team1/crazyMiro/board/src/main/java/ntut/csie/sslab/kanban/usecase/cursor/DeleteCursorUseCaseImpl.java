package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.cursor.delete.DeleteCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.delete.DeleteCursorUseCase;

public class DeleteCursorUseCaseImpl implements DeleteCursorUseCase {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public DeleteCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(DeleteCursorInput input, CqrsCommandOutput output) {

        Cursor cursor = cursorRepository.findById(input.getCursorId()).get();

        cursor.deleteCursor();
        cursorRepository.deleteById(input.getCursorId());
        domainEventBus.postAll(cursor);

        output.setId(cursor.getId())
                .setExitCode(ExitCode.SUCCESS);

    }

    @Override
    public DeleteCursorInput newInput() {
        return new DeleteCursorInputImpl();
    }

    private class DeleteCursorInputImpl implements DeleteCursorInput {
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
