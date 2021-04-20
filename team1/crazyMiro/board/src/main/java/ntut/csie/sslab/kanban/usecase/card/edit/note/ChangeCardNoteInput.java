package ntut.csie.sslab.kanban.usecase.card.edit.note;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeCardNoteInput extends Input {
    public void setCardId(String cardId);

    public String getCardId();

    public void setNewNotes(String newNotes);

    public String getNewNotes();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();

    public void setBoardId(String boardId);

    public String getBoardId();
}
