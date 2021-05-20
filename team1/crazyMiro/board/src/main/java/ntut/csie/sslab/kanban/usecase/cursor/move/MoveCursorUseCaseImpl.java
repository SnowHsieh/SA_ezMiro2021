package ntut.csie.sslab.kanban.usecase.cursor.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

public class MoveCursorUseCaseImpl implements MoveCursorUseCase {

    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public MoveCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveCursorInput input, CqrsCommandOutput output) {
        try{
            Cursor cursor = cursorRepository.findCursorByUserId(input.getUserId()).get();
            if(cursor.getPosition().equals(input.getPosition()))
                return;

            cursor.setPosition(input.getPosition());

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
    public MoveCursorInput newInput() {
        return new MoveCursorInputImpl();
    }

    private class MoveCursorInputImpl implements MoveCursorInput {
        private String userId;
        private Coordinate position;

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }
    }
}
