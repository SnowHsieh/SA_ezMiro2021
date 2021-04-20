package ntut.csie.sslab.kanban.usecase.card.edit.deadline;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeCardDeadlineInput extends Input {
    public void setCardId(String cardId);

    public String getCardId();

    public void setNewDeadline(String newDeadline);

    public String getNewDeadline();

    public void setBoardId(String boardId);

    public String getBoardId();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();
}
