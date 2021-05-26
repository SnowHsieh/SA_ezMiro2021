package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import ntut.csie.team5.entity.model.board.Cursor;

import javax.persistence.*;

@Entity
@Table(name = "cursor")
public class CursorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "position_x")
    private int positionX;

    @Column(name = "position_y")
    private int positionY;

    public CursorData() {
    }

    public CursorData(String userId, int positionX, int positionY) {
        this();
        this.userId = userId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
