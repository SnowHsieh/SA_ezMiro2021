package ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stickynote")
public class StickyNoteData {
    @Id
    @Column(name = "stickynote_id")
    private String stickynoteId;

    @Column(name = "board_id")
    private String boardId;

    @Column(name = "content")
    private String content;

    //position
    @Column(name = "position_x")
    private double positionX;

    @Column(name = "position_y")
    private double positionY;

    //style
    @Column(name = "font_size")
    private int fontSize;

    @Column(name = "shape_kind")
    private int shapeKind;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "color")
    private String color;

    public StickyNoteData(){

    }
    public StickyNoteData(String boardId, String stickynoteId, double positionX, double positionY, String content,int fontSize, int shapeKind, double width, double height, String color) {
        this.stickynoteId = stickynoteId;
        this.boardId = boardId;
        this.content = content;
        this.positionX = positionX;
        this.positionY = positionY;
        this.fontSize = fontSize;
        this.shapeKind = shapeKind;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    public String getStickyNoteId() {
        return stickynoteId;
    }

    public void setStickyNoteId(String stickynoteId) {
        this.stickynoteId = stickynoteId;
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

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getShapeKind() {
        return shapeKind;
    }

    public void setShapeKind(int shapeKind) {
        this.shapeKind = shapeKind;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "StickyNoteData{" +
                "stickynoteId='" + stickynoteId + '\'' +
                ", boardId='" + boardId + '\'' +
                ", content='" + content + '\'' +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", fontSize=" + fontSize +
                ", shapeKind=" + shapeKind +
                ", width=" + width +
                ", height=" + height +
                ", color='" + color + '\'' +
                '}';
    }
}
