package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker;

import ntut.csie.sslab.miro.entity.model.figure.FigureType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sticker")
public class StickerData {

    @Id
    @Column(name="figure_id")
    private String figureId;

    @Column(name="board_id")
    private String boardId;

    @Column(name="content")
    private String content;

    @Column(name="width")
    private int width;

    @Column(name="length")
    private int length;

    @Column(name="color")
    private String color;

    @Column(name="x")
    private long x;

    @Column(name="y")
    private long y;

    @Column(name="type")
    private FigureType type;

    public StickerData() {
    }

    public StickerData(String figureId, String boardId, String content, int width, int length, String color, long x, long y, FigureType type) {
        this.figureId = figureId;
        this.boardId = boardId;
        this.content = content;
        this.width = width;
        this.length = length;
        this.color = color;
        this.x = x;
        this.y = y;
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public FigureType getType() {
        return type;
    }

    public void setType(FigureType type) {
        this.type = type;
    }
}
