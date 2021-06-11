package ntut.csie.team5.adapter.gateway.repository.springboot.figure.line;

import javax.persistence.*;

@Entity
@Table(name = "line")
public class LineData {

    @Id
    @Column(name = "line_id")
    private String lineId;

    @Column(name = "board_id", nullable = false)
    private String boardId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endpoint_a_id")
    private EndpointData endpointDataA;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endpoint_b_id")
    private EndpointData endpointDataB;

    public LineData() {
    }

    public LineData(String lineId, String boardId, EndpointData endpointDataA, EndpointData endpointDataB) {
        this.lineId = lineId;
        this.boardId = boardId;
        this.endpointDataA = endpointDataA;
        this.endpointDataB = endpointDataB;
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

    public EndpointData getEndpointDataA() {
        return endpointDataA;
    }

    public void setEndpointDataA(EndpointData endpointDataA) {
        this.endpointDataA = endpointDataA;
    }

    public EndpointData getEndpointDataB() {
        return endpointDataB;
    }

    public void setEndpointDataB(EndpointData endpointDataB) {
        this.endpointDataB = endpointDataB;
    }
}
