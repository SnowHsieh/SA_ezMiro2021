package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import javax.persistence.*;

@Entity
@Table(name="committed_card")
public class CommittedCardData {

    @EmbeddedId
    CommittedCardDataId committedCardDataId;

    @Column(name="card_order")
    private int order;

    public CommittedCardData(){}

    public CommittedCardData(CommittedCardDataId committedCardDataId, int order) {
        this.committedCardDataId = committedCardDataId;
        this.order = order;
    }

    public CommittedCardDataId getCommittedCardDataId() {
        return committedCardDataId;
    }

    public void setCommittedCardDataId(CommittedCardDataId committedCardDataId) {
        this.committedCardDataId = committedCardDataId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
