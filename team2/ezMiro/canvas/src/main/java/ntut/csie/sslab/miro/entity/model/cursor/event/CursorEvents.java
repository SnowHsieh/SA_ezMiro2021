package ntut.csie.sslab.miro.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import java.time.Instant;
import java.util.UUID;

public interface CursorEvents extends DomainEvent {
    String getCursorId();
    String getBoardId();
    class CursorCreated implements CursorEvents {
        private final UUID id;
        private final String cursorId;
        private final String boardId;
        private final String userId;
        private final Coordinate coordinate;
        private final Instant occurredOn;

        public CursorCreated(UUID id, String cursorId, String boardId, String userId, Coordinate coordinate, Instant occurredOn) {
            this.id = id;
            this.cursorId = cursorId;
            this.boardId = boardId;
            this.userId = userId;
            this.coordinate = coordinate;
            this.occurredOn = occurredOn;
        }

        public String getCursorId() {
            return cursorId;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getUserId() {
            return userId;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }

/////////////////////////////////////////////////////////////////

    class CursorMoved implements CursorEvents {
        private final UUID id;
        private final String cursorId;
        private final String boardId;
        private final String userId;
        private final Coordinate coordinate;
        private final Instant occurredOn;

        public CursorMoved(UUID id, String cursorId, String boardId, String userId, Coordinate coordinate, Instant occurredOn) {
            this.id = id;
            this.cursorId = cursorId;
            this.boardId = boardId;
            this.userId = userId;
            this.coordinate = coordinate;
            this.occurredOn = occurredOn;
        }

        public String getCursorId() {
            return cursorId;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getUserId() {
            return userId;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }

/////////////////////////////////////////////////////////////////

    class CursorRemoved implements CursorEvents {
        private final UUID id;
        private final String cursorId;
        private final String boardId;
        private final Instant occurredOn;

        public CursorRemoved(UUID id, String cursorId, String boardId, Instant occurredOn) {
            this.id = id;
            this.cursorId = cursorId;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getCursorId() {
            return cursorId;
        }

        public String getBoardId() {
            return boardId;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }
}