package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class Cursor extends ValueObject {

    private final String userId;
    private final int positionX;
    private final int positionY;

    public Cursor(String userId, int positionX, int positionY) {
        this.userId = userId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String userId() {
        return userId;
    }

    public int positionX() {
        return positionX;
    }

    public int positionY() {
        return positionY;
    }
}
