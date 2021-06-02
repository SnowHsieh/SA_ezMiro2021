package ntut.csie.sslab.miro.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import java.time.Instant;
import java.util.UUID;

public interface LineEvents extends DomainEvent {
     String getBoardId();
     class LineCreated implements LineEvents {
         private UUID id;
         private String boardId;
         private String lineId;
         private String startConnectableFigureId;
         private String endConnectableFigureId;
         private Coordinate startOffset;
         private Coordinate endOffset;
         private ArrowStyle startArrowStyle;
         private ArrowStyle endArrowStyle;
         private Instant occurredOn;

         public LineCreated(UUID id, String boardId, String lineId, String startConnectableFigureId, String endConnectableFigureId, Coordinate startOffset, Coordinate endOffset, ArrowStyle startArrowStyle, ArrowStyle endArrowStyle, Instant occurredOn) {
             this.id = id;
             this.boardId = boardId;
             this.lineId = lineId;
             this.startConnectableFigureId = startConnectableFigureId;
             this.endConnectableFigureId = endConnectableFigureId;
             this.startOffset = startOffset;
             this.endOffset = endOffset;
             this.startArrowStyle = startArrowStyle;
             this.endArrowStyle = endArrowStyle;
         }

         public void setId(UUID id) {
             this.id = id;
         }

         public String getBoardId() {
             return boardId;
         }

         public void setBoardId(String boardId) {
             this.boardId = boardId;
         }

         public String getLineId() {
             return lineId;
         }

         public void setLineId(String lineId) {
             this.lineId = lineId;
         }

         public String getStartConnectableFigureId() {
             return startConnectableFigureId;
         }

         public void setStartConnectableFigureId(String startConnectableFigureId) {
             this.startConnectableFigureId = startConnectableFigureId;
         }

         public String getEndConnectableFigureId() {
             return endConnectableFigureId;
         }

         public void setEndConnectableFigureId(String endConnectableFigureId) {
             this.endConnectableFigureId = endConnectableFigureId;
         }

         public Coordinate getStartOffset() {
             return startOffset;
         }

         public void setStartOffset(Coordinate startOffset) {
             this.startOffset = startOffset;
         }

         public Coordinate getEndOffset() {
             return endOffset;
         }

         public void setEndOffset(Coordinate endOffset) {
             this.endOffset = endOffset;
         }

         public ArrowStyle getStartArrowStyle() {
             return startArrowStyle;
         }

         public void setStartArrowStyle(ArrowStyle startArrowStyle) {
             this.startArrowStyle = startArrowStyle;
         }

         public ArrowStyle getEndArrowStyle() {
             return endArrowStyle;
         }

         public void setEndArrowStyle(ArrowStyle endArrowStyle) {
             this.endArrowStyle = endArrowStyle;
         }

         @Override
         public UUID getId() {
             return id;
         }

         @Override
         public Instant getOccurredOn() {
             return null;
         }
     }

///////////////////////////////////////////////////////////////////////

    class LineMoved implements LineEvents {
        private final UUID id;
        private final String lineId;
        private final Coordinate startOffset;
        private final Coordinate endOffset;
        private final String boardId;
        private final Instant occurredOn;

        public LineMoved(UUID id, String lineId, Coordinate startOffset, Coordinate endOffset, String boardId, Instant occurredOn) {
            this.id = id;
            this.lineId = lineId;
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        public String getLineId() {
            return lineId;
        }

        public Coordinate getStartOffset() {
            return startOffset;
        }

        public Coordinate getEndOffset() {
            return endOffset;
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

    class LineDeleted implements LineEvents {
        private final UUID id;
        private final String boardId;
        private final String lineId;
        private final Instant occurredOn;

        public LineDeleted(UUID id, String lineId, String boardId, Instant occurredOn) {
            this.id = id;
            this.boardId = boardId;
            this.lineId = lineId;
            this.occurredOn = occurredOn;
        }

        public String getBoardId() {
            return boardId;
        }

        public String getLineId() {
            return lineId;
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

    class LinePointMoved implements LineEvents {
        private final UUID id;
        private final String lineId;
        private final LinePoint linePoint;
        private final Coordinate newOffset;
        private final String boardId;
        private final Instant occurredOn;

        public LinePointMoved(UUID id, String lineId, LinePoint linePoint, Coordinate newOffset, String boardId, Instant occurredOn) {
            this.id = id;
            this.lineId = lineId;
            this.linePoint = linePoint;
            this.newOffset = newOffset;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        @Override
        public UUID getId() {
            return id;
        }

        public String getLineId() {
            return lineId;
        }

        public LinePoint getLinePoint() {
            return linePoint;
        }

        public Coordinate getNewOffset() {
            return newOffset;
        }

        public String getBoardId() {
            return boardId;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }

    class LineConnectedToFigure implements LineEvents {
        private final UUID id;
        private final String lineId;
        private final LinePoint linePoint;
        private final String figureId;
        private final Coordinate offset;
        private final String boardId;
        private final Instant occurredOn;

        public LineConnectedToFigure(UUID id, String lineId, LinePoint linePoint, String figureId, Coordinate offset, String boardId, Instant occurredOn) {
            this.id = id;
            this.lineId = lineId;
            this.linePoint = linePoint;
            this.figureId = figureId;
            this.offset = offset;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }

        public String getLineId() {
            return lineId;
        }

        public LinePoint getLinePoint() {
            return linePoint;
        }

        public String getFigureId() {
            return figureId;
        }

        public Coordinate getOffset() {
            return offset;
        }

        public String getBoardId() {
            return boardId;
        }
    }

    class LineDisconnectFromFigure implements LineEvents {
        private final UUID id;
        private final String lineId;
        private final LinePoint linePoint;
        private final String figureId;
        private final Coordinate offset;
        private final String boardId;
        private final Instant occurredOn;

        public LineDisconnectFromFigure(UUID id, String lineId, LinePoint linePoint, String figureId, Coordinate offset, String boardId, Instant occurredOn) {
            this.id = id;
            this.lineId = lineId;
            this.linePoint = linePoint;
            this.figureId = figureId;
            this.offset = offset;
            this.boardId = boardId;
            this.occurredOn = occurredOn;
        }

        @Override
        public UUID getId() {
            return id;
        }

        public String getLineId() {
            return lineId;
        }

        public LinePoint getLinePoint() {
            return linePoint;
        }

        public String getFigureId() {
            return figureId;
        }

        public Coordinate getOffset() {
            return offset;
        }

        public String getBoardId() {
            return boardId;
        }

        @Override
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }
}