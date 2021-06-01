package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import java.time.Instant;
import java.util.UUID;

public interface BoardEvents extends DomainEvent {

    class BoardCreated implements BoardEvents {
        private final UUID id;
        private final String teamId;
        private final String boardId;
        private final String boardName;
        private final Instant occurredOn;

        public BoardCreated(UUID id, String teamId, String boardId, String boardName, Instant occurredOn) {
            this.id = id;
            this.teamId = teamId;
            this.boardId = boardId;
            this.boardName = boardName;
            this.occurredOn = occurredOn;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getBoardName() {
            return boardName;
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

    class FigureCommitted implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final Instant occurredOn;

        public FigureCommitted(UUID id, String boardId, String noteId, Instant occurredOn) {
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

/////////////////////////////////////////////////////////////////

    class FigureCommittedToBack implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final Instant occurredOn;

        public FigureCommittedToBack(UUID id, String boardId, String noteId, Instant occurredOn) {
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

/////////////////////////////////////////////////////////////////

    class FigureCommittedToFront implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final Instant occurredOn;

        public FigureCommittedToFront(UUID id, String boardId, String noteId, Instant occurredOn) {
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

/////////////////////////////////////////////////////////////////

    class NoteRemovedFromBoard implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String noteId;
        private final Instant occurredOn;

        public NoteRemovedFromBoard(UUID id, String boardId, String noteId, Instant occurredOn) {
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

/////////////////////////////////////////////////////////////////

    class BoardEntered implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String userId;
        private final Instant occurredOn;

        public BoardEntered(UUID id, String boardId, String userId, Instant occurredOn) {
            this.id = id;
            this.boardId = boardId;
            this.userId = userId;
            this.occurredOn = occurredOn;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getUserId() {
            return userId;
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

    class BoardLeft implements BoardEvents {
        private final UUID id;
        private final String boardId;
        private final String userId;
        private final Instant occurredOn;

        public BoardLeft(UUID id, String boardId, String userId, Instant occurredOn) {
            this.id = id;
            this.boardId = boardId;
            this.userId = userId;
            this.occurredOn = occurredOn;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getUserId() {
            return userId;
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