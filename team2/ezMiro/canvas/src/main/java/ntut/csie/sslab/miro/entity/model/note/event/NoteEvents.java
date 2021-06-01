package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import java.time.Instant;
import java.util.UUID;

public interface NoteEvents extends DomainEvent {
    String getNoteId();
    String getBoardId();
    class NoteCreated implements NoteEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final String description;
        private final String color;
        private final Coordinate coordinate;
        private final double width;
        private final double height;
        private final Instant occurredOn;

        public NoteCreated(UUID id, String boardId, String noteId, String description, String color, Coordinate coordinate, double width, double height, Instant occurredOn) {
            this.id = id;
            this.boardId = boardId;
            this.noteId = noteId;
            this.description = description;
            this.color = color;
            this.coordinate = coordinate;
            this.width = width;
            this.height = height;
            this.occurredOn = occurredOn;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getNoteId() {
            return noteId;
        }

        public String getDescription() {
            return description;
        }

        public String getColor() {
            return color;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
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

///////////////////////////////////////////////////////////////////////

    class NoteDescriptionChanged implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final String description;
        private final String boardId;
        private final Instant occurredOn;

        public NoteDescriptionChanged(UUID id, String noteId, String description, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.description = description;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
        }

        public String getDescription() {
            return description;
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

///////////////////////////////////////////////////////////////////////

    class NoteColorChanged implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final String color;
        private final String boardId;
        private final Instant occurredOn;

        public NoteColorChanged(UUID id, String noteId, String color, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.color = color;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
        }

        public String getColor() {
            return color;
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

///////////////////////////////////////////////////////////////////////

    class NoteSizeChanged implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final double height;
        private final double width;
        private final String boardId;
        private final Instant occurredOn;

        public NoteSizeChanged(UUID id, String noteId, double height, double width, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.height = height;
            this.width = width;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
        }

        public double getHeight() {
            return height;
        }

        public double getWidth() {
            return width;
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

///////////////////////////////////////////////////////////////////////

    class NoteMoved implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final Coordinate coordinate;
        private final String boardId;
        private final Instant occurredOn;

        public NoteMoved(UUID id, String noteId, Coordinate coordinate, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.coordinate = coordinate;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
        }

        public Coordinate getCoordinate() {
            return coordinate;
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

///////////////////////////////////////////////////////////////////////

    class NoteBroughtToFront implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final String boardId;
        private final Instant occurredOn;

        public NoteBroughtToFront(UUID id, String noteId, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
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

///////////////////////////////////////////////////////////////////////

    class NoteSentToBack implements NoteEvents {
        private final UUID id;
        private final String noteId;
        private final String boardId;
        private final Instant occurredOn;

        public NoteSentToBack(UUID id, String noteId, String boardId, Instant occurredOn) {
            this.id = id;
            this.noteId = noteId;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getNoteId() {
            return noteId;
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

///////////////////////////////////////////////////////////////////////

    class NoteDeleted implements NoteEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final Instant occurredOn;

        public NoteDeleted(UUID id, String boardId, String noteId, Instant occurredOn) {
            this.id = id;
            this.boardId = boardId;
            this.noteId = noteId;
            this.occurredOn = occurredOn;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getNoteId() {
            return noteId;
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