package ntut.csie.sslab.kanban.usecase.card.edit;

import ntut.csie.sslab.ddd.usecase.Input;

public interface EditCardInput extends Input{
	public String getCardId();
	
	public void setCardId(String cardId);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public String getCategoryId();
	
	public void setCategoryId(String categoryId);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getEstimate();
	
	public void setEstimate(String estimate);
	
	public String getNotes();
	
	public void setNotes(String notes);
	
	public String getDeadline();
	
	public void setDeadline(String deadLine);

	public String getType();

	public void setType(String type);
}
