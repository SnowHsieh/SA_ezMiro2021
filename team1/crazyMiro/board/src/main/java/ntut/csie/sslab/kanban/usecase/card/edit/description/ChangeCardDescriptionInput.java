package ntut.csie.sslab.kanban.usecase.card.edit.description;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeCardDescriptionInput extends Input {
    public void setCardId(String cardId);

    public String getCardId();

    public void setNewDescription(String newDescription);

    public String getNewDescription();

    public void setBoardId(String boardId);

    public String getBoardId();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();
}
