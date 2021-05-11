package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorUseCase;

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
            Cursor cursor = cursorRepository.findById(input.getCursorId()).get();
            cursor.setPosition(input.getPosition());

            cursorRepository.save(cursor);
            domainEventBus.postAll(cursor);

            output.setId(input.getCursorId())
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
        private String cursorId;
        private Coordinate position;

        @Override
        public String getCursorId() {
            return cursorId;
        }

        @Override
        public void setCursorId(String cursorId) {
            this.cursorId = cursorId;
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
