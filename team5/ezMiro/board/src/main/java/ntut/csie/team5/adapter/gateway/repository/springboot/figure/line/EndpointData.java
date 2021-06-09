package ntut.csie.team5.adapter.gateway.repository.springboot.figure.line;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class EndpointData implements Serializable {

    @Column(name = "id")
    private String id;

    @Column(name = "position_x")
    private int positionX;

    @Column(name = "position_y")
    private int positionY;

    @Column(name = "connected_figure_id", columnDefinition = "LONGVARBINARY")
    private String connectedFigureId;

    public EndpointData() {
    }

    public EndpointData(String id, int positionX, int positionY, String connectedFigureId) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.connectedFigureId = connectedFigureId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getConnectedFigureId() {
        return connectedFigureId;
    }

    public void setConnectedFigureId(String connectedFigureId) {
        this.connectedFigureId = connectedFigureId;
    }
}
