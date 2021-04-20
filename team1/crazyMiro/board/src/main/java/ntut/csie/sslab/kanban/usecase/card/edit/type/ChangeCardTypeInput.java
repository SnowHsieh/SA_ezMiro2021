package ntut.csie.sslab.kanban.usecase.card.edit.type;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeCardTypeInput extends Input {
    public void setCardId(String workItemId);

    public String getCardId();

    public void setNewType(String newType);

    public String getNewType();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();

    public void setBoardId(String boardId);

    public String getBoardId();
}
