package ntut.csie.sslab.kanban.usecase.card.edit.estimate;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeCardEstimateInput extends Input {
    public void setCardId(String cardId);

    public String getCardId();

    public void setNewEstimate(String newEstimate);

    public String getNewEstimate();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();

    public void setBoardId(String boardId);

    public String getBoardId();
}
