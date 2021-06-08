package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "line")
public class LineData {
    @Id
    @Column(name = "line_id")
    private String lineId;

    @Column(name = "board_id")
    private String boardId;

    @Column(name = "stroke_width")
    private int strokeWidth;

    @Column(name = "color")
    private String color;

    @Column(name = "src_endpoint_kind")
    private int srcEndpointKind;

    @Column(name = "dest_endpoint_kind")
    private int destEndpointKind;

    @Column(name = "src_connectable_figure_id")
    private String srcConnectableFigureId;

    @Column(name = "dest_connectable_figure_id")
    private String destConnectableFigureId;

    @OrderBy("pointOrder")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PositionData> positionDataList;

    public LineData() {
    }

    public LineData(String boardId, String lineId, List<PositionData> positionDataList, int strokeWidth, String color, int srcEndpointKind, int destEndpointKind) {
        this.lineId = lineId;
        this.boardId = boardId;
        this.strokeWidth = strokeWidth;
        this.color = color;
        this.srcEndpointKind = srcEndpointKind;
        this.destEndpointKind = destEndpointKind;
        this.positionDataList = new HashSet<PositionData>(positionDataList);
        this.srcConnectableFigureId = "";
        this.destConnectableFigureId = "";
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSrcEndpointKind() {
        return srcEndpointKind;
    }

    public void setSrcEndpointKind(int srcEndpointKind) {
        this.srcEndpointKind = srcEndpointKind;
    }

    public int getDestEndpointKind() {
        return destEndpointKind;
    }

    public void setDestEndpointKind(int destEndpointKind) {
        this.destEndpointKind = destEndpointKind;
    }

    public List<PositionData> getPositionDataList() {
        return new ArrayList<>(this.positionDataList);
    }

    public String getSrcConnectableFigureId() {
        return srcConnectableFigureId;
    }

    public void setSrcConnectableFigureId(String srcConnectableFigureId) {
        this.srcConnectableFigureId = srcConnectableFigureId;
    }

    public String getDestConnectableFigureId() {
        return destConnectableFigureId;
    }

    public void setDestConnectableFigureId(String destConnectableFigureId) {
        this.destConnectableFigureId = destConnectableFigureId;
    }
}
