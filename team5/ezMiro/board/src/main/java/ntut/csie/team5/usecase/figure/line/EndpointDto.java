package ntut.csie.team5.usecase.figure.line;

public class EndpointDto {

    private String id;
    private int positionX;
    private int positionY;
    private String connectedFigureId;

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
