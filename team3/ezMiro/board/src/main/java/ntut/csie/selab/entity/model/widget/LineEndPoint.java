package ntut.csie.selab.entity.model.widget;

public enum LineEndPoint {
    HEAD("head"),
    TAIL("tail");

    private final String endPoint;

    LineEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }
}
