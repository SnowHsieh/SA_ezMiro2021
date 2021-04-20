package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommittedCardDataId implements Serializable {


    @Column(name="card_id", nullable = false)
    private String cardId;

    @Column(name="lane_id", nullable = false)
    private String laneId;

    public CommittedCardDataId(){}

    public CommittedCardDataId(String cardId, String laneId) {
        this.cardId = cardId;
        this.laneId = laneId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommittedCardDataId)) return false;
        CommittedCardDataId that = (CommittedCardDataId) o;
        return Objects.equals(getCardId(), that.getCardId()) &&
                Objects.equals(getLaneId(), that.getLaneId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardId(), getLaneId());
    }
}
