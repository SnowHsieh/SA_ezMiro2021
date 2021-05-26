package ntut.csie.selab.adapter.gateway.repository.springboot.board;


import javax.persistence.*;

@Entity
@Table(name="cursor")
public class CursorData {

    @Id
    @Column(name="user_id", nullable=false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private BoardData board;

    @Column(name = "point_x")
    private int pointX;

    @Column(name = "point_y")
    private int pointY;

    public CursorData() {

    }
    public CursorData(String userId, int pointX, int pointY) {
        this.userId = userId;
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BoardData getBoard() {
        return board;
    }

    public void setBoard(BoardData board) {
        this.board = board;
    }
}
