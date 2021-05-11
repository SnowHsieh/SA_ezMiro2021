package ntut.csie.sslab.kanban.usecase.cursor.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

import java.util.UUID;

public class CreateCursorUseCaseImpl implements CreateCursorUseCase {
    private DomainEventBus domainEventBus;
    private CursorRepository cursorRepository;

    public CreateCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
        this.cursorRepository = cursorRepository;
    }


    @Override
    public void execute(CreateCursorInput input, CqrsCommandOutput output) {
        try {
            Cursor cursor = new Cursor(input.getBoardId(), UUID.randomUUID().toString(), input.getIp());
            cursorRepository.save(cursor);
            domainEventBus.postAll(cursor);
            output.setId(cursor.getCursorId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){

            output.setMessage(e.getMessage())
                    .setExitCode(ExitCode.FAILURE);
        }

    }

    @Override
    public CreateCursorInput newInput() {
        return new CreateCursorInputImpl();
    }

    private class CreateCursorInputImpl implements CreateCursorInput {
        private String boardId;
        private String ip;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getIp() {
            return ip;
        }

        @Override
        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
