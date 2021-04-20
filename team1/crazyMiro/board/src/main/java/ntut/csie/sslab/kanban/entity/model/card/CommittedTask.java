package ntut.csie.sslab.kanban.entity.model.card;

public class CommittedTask {
    private String taskId;
    private String cardId;
    private int order;

    public CommittedTask() {}

    public CommittedTask(String taskId, String cardId, int order) {
        this.taskId = taskId;
        this.cardId = cardId;
        this.order = order;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
