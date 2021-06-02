package ntut.csie.team5.entity.model.figure.line;

import ntut.csie.sslab.ddd.model.Entity;
import ntut.csie.team5.entity.model.board.BoardSessionId;

public class Endpoint extends Entity<String> {

    private int positionX;
    private int positionY;
    private String connectedFigureId;

    public Endpoint(String endpointId, int positionX, int positionY, String connectedFigureId) {
        super(endpointId);
        this.positionX = positionX;
        this.positionY = positionY;
        this.connectedFigureId = connectedFigureId;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Endpoint))
            return false;

        Endpoint target = (Endpoint) o;
        return target.id.equals(id) ;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
