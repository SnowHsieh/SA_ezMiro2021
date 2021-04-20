package ntut.csie.sslab.kanban.usecase.card.edit;

import ntut.csie.sslab.ddd.usecase.Output;

public interface EditCardOutput extends Output{
	public void setCardId(String cardId);
	
	public String getCardId();
}
