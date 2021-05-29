package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class PositionData {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "line_id")
    private String lineId;

    @Column(name="point_order")
    private int pointOrder;
    //position
    @Column(name = "position_x")
    private double positionX;

    @Column(name = "position_y")
    private double positionY;

    public PositionData() {

    }

    public PositionData(String lineId, int pointOrder, double positionX, double positionY) {
        this.lineId = lineId;
        this.pointOrder = pointOrder;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getLineId() {
        return lineId;
    }

    public int getPointOrder() {
        return pointOrder;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
