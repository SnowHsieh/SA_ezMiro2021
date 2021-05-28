package ntut.csie.sslab.miro.usecase.cursor.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;

public class MoveCursorUseCaseImpl implements MoveCursorUseCase {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public MoveCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
        this.cursorRepository = cursorRepository;
    }

    @Override
    public void execute(MoveCursorInput input, CqrsCommandOutput output) {
        Cursor cursor = cursorRepository.findById(input.getCursorId()).orElse(null);
        if (cursor == null) {
            output.setId(input.getCursorId())
                    .setMessage("Move cursor failed: cursor not found, cursor id = " + input.getCursorId());
            return;
        }
        cursor.move(input.getCoordinate());

        cursorRepository.save(cursor);
        domainEventBus.postAll(cursor);

        output.setId(cursor.getId());
    }

    @Override
    public MoveCursorInput newInput() {
        return new MoveCursorInputImpl();
    }


    private class MoveCursorInputImpl implements MoveCursorInput {
        private String cursorId;
        private Coordinate coordinate;

        @Override
        public String getCursorId() {
            return cursorId;
        }

        @Override
        public void setCursorId(String cursorId) {
            this.cursorId = cursorId;
        }

        @Override
        public Coordinate getCoordinate() {
            return coordinate;
        }

        @Override
        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }
    }
}