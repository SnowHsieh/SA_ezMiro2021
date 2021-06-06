package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="line")
public class LineData {

    @Id
    @Column(name="figure_id")
    private String figureId;

    @Column(name="board_id")
    private String boardId;

    @Column(name="source_id")
    private String sourceId;

    @Column(name="target_id")
    private String targetId;

    @Column(name="source_x")
    private long sourceX;

    @Column(name="source_y")
    private long sourceY;

    @Column(name="target_x")
    private long targetX;

    @Column(name="target_y")
    private long targetY;

    public LineData() {

    }

    public LineData(String figureId, String boardId, String sourceId, String targetId, long sourceX, long sourceY, long targetX, long targetY) {
        this.figureId = figureId;
        this.boardId = boardId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public long getSourceX() {
        return sourceX;
    }

    public void setSourceX(long sourceX) {
        this.sourceX = sourceX;
    }

    public long getSourceY() {
        return sourceY;
    }

    public void setSourceY(long sourceY) {
        this.sourceY = sourceY;
    }

    public long getTargetX() {
        return targetX;
    }

    public void setTargetX(long targetX) {
        this.targetX = targetX;
    }

    public long getTargetY() {
        return targetY;
    }

    public void setTargetY(long targetY) {
        this.targetY = targetY;
    }
}
