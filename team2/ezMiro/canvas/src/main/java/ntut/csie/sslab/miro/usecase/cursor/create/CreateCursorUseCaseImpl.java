package ntut.csie.sslab.miro.usecase.cursor.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.entity.model.cursor.CursorBuilder;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;

public class CreateCursorUseCaseImpl implements CreateCursorUseCase {
    private DomainEventBus domainEventBus;
    private CursorRepository cursorRepository;

    public CreateCursorUseCaseImpl(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
        this.cursorRepository = cursorRepository;
    }

    @Override
    public void execute(CreateCursorInput input, CqrsCommandOutput output) {
        Cursor cursor = CursorBuilder.newInstance()
                .boardId(input.getBoardId())
                .userId(input.getUserId())
                .coordinate(input.getCoordinate())
                .build();

        cursorRepository.save(cursor);
        domainEventBus.postAll(cursor);

        output.setId(cursor.getId());
    }

    @Override
    public CreateCursorInput newInput() {
        return new CreateCursorInputImpl();
    }

    private static class CreateCursorInputImpl implements CreateCursorInput {
        private String boardId;
        private String userId;
        private Coordinate coordinate;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
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