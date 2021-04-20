package ntut.csie.sslab.kanban.usecase.workflow;

public class CommittedCardDto {

    private String cardId;
    private String laneId;
    private int order;


    public CommittedCardDto(){}
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
