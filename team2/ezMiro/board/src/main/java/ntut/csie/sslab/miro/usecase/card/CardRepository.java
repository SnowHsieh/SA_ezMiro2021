package ntut.csie.sslab.miro.usecase.card;


import ntut.csie.sslab.miro.entity.model.card.Card;
import ntut.csie.sslab.ddd.usecase.AbstractRepository;

import java.util.List;

public interface CardRepository extends AbstractRepository<Card, String> {

	List<Card> getCardsByLaneId(String swimLaneId);

	List<Card> getCardsByCategoryId(String categoryId);

	List<Card> getCardsByWorkflowId(String workflowId);

	List<Card> getCardsByBoardId(String boardId);

	List<Card> findCardsByTagId(String tagId);
}

