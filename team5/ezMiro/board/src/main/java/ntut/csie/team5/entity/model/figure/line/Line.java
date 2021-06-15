package ntut.csie.team5.entity.model.figure.line;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.event.LineConnected;
import ntut.csie.team5.entity.model.figure.line.event.LineDrew;
import ntut.csie.team5.entity.model.figure.line.event.LineEndpointMoved;
import ntut.csie.team5.entity.model.figure.line.event.LineMoved;
import ntut.csie.team5.entity.model.figure.line.event.LineDisconnected;

public class Line extends Figure {

    private Endpoint endpointA;
    private Endpoint endpointB;

    public Line(String figureId, String boardId, Endpoint endpointA, Endpoint endpointB, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.endpointA = endpointA;
        this.endpointB = endpointB;
        addDomainEvent(new LineDrew(figureId, boardId, endpointA, endpointB, figureType));
    }

    public void moveEndpoint(String endpointId, int positionX, int positionY) {
        if(this.endpointA.getId().equals(endpointId)) {
            int oldPositionX = this.endpointA.getPositionX();
            int oldPositionY = this.endpointA.getPositionY();
            this.endpointA.setPositionX(positionX);
            this.endpointA.setPositionY(positionY);
            addDomainEvent(new LineEndpointMoved(getId(), getEndpointA().getId(), oldPositionX, oldPositionY, positionX, positionY, getBoardId(), getFigureType()));
        } else if (this.endpointB.getId().equals(endpointId)) {
            int oldPositionX = this.endpointB.getPositionX();
            int oldPositionY = this.endpointB.getPositionY();
            this.endpointB.setPositionX(positionX);
            this.endpointB.setPositionY(positionY);
            addDomainEvent(new LineEndpointMoved(getId(), getEndpointB().getId(), oldPositionX, oldPositionY, positionX, positionY, getBoardId(), getFigureType()));
        }
    }

    public void moveLine(int offsetX, int offsetY) {
        this.endpointA.setPositionX(this.endpointA.getPositionX() + offsetX);
        this.endpointA.setPositionY(this.endpointA.getPositionY() + offsetY);
        this.endpointB.setPositionX(this.endpointB.getPositionX() + offsetX);
        this.endpointB.setPositionY(this.endpointB.getPositionY() + offsetY);
        addDomainEvent(new LineMoved(getId(), offsetX, offsetY, getBoardId(), getFigureType()));
    }

    public void connectToFigure(String endpointId, String connectFigureId) {
        if (this.endpointA.getId().equals(endpointId)) {
            this.endpointA.setConnectedFigureId(connectFigureId);
            addDomainEvent(new LineConnected(getId(), getEndpointA().getId(), connectFigureId, getBoardId(), getFigureType()));
        } else if (this.endpointB.getId().equals(endpointId)) {
            this.endpointB.setConnectedFigureId(connectFigureId);
            addDomainEvent(new LineConnected(getId(), getEndpointB().getId(), connectFigureId, getBoardId(), getFigureType()));
        }
    }

    public void disconnectToFigure(String endpointId) {
        if (this.endpointA.getId().equals(endpointId)) {
            String connectedFigureId = this.endpointA.getConnectedFigureId();
            this.endpointA.setConnectedFigureId("");
            addDomainEvent(new LineDisconnected(getId(), getEndpointA().getId(), connectedFigureId, getBoardId(), getFigureType()));
        } else if (this.endpointB.getId().equals(endpointId)) {
            String connectedFigureId = this.endpointB.getConnectedFigureId();
            this.endpointB.setConnectedFigureId("");
            addDomainEvent(new LineDisconnected(getId(), getEndpointB().getId(), connectedFigureId, getBoardId(), getFigureType()));
        }
    }

    public Endpoint getEndpointA() {
        return endpointA;
    }

    public void setEndpointA(Endpoint endpointA) {
        this.endpointA = endpointA;
    }

    public Endpoint getEndpointB() {
        return endpointB;
    }

    public void setEndpointB(Endpoint endpointB) {
        this.endpointB = endpointB;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Line [ id = ");
        sb.append(id);
        sb.append(", endpoitnA = ");
        sb.append(endpointA.toString());
        sb.append(", endpoitnB = ");
        sb.append(endpointB.toString());
        sb.append(" ]");
        return sb.toString();
    }
}
