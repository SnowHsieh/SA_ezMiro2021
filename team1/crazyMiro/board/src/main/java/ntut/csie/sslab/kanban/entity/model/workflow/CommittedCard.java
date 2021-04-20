package ntut.csie.sslab.kanban.entity.model.workflow;


import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedCard extends ValueObject {

    private String cardId;
    private String laneId;
    private int order;

    public CommittedCard(){}

    public CommittedCard(String cardId, String laneId, int order){
        this.cardId = cardId;
        this.laneId = laneId;
        this.order = order;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}