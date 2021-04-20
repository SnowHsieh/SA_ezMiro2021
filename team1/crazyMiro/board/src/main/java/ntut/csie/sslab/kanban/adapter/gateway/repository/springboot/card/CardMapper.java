package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;

public class CardMapper {

	public static Card transformToDomain(CardData data) {

		CardType type = null;
		switch(data.getType()) {
			case("General"):
				type = CardType.General;
				break;
			case("Expedite"):
				type = CardType.Expedite;
				break;
		}

		Card card = new Card(data.getUserId(),
				"",
				data.getBoardId(),
				data.getWorkflowId(),
				data.getLaneId(),
				data.getCardId(),
				data.getDescription(),
				data.getEstimate(),
				data.getNotes(),
				data.getDeadline(),
				type);

		card.clearDomainEvents();
		return card;
	}

	public static List<Card> transformToDomain(List<CardData> datas) {
		List<Card> result = new ArrayList<>();
		datas.forEach(x -> result.add(transformToDomain(x)));
		return result;
	}


	public static CardData transformToData(Card card) {

		CardData data = new CardData();
		data.setCardId(card.getCardId());
		data.setBoardId(card.getBoardId());
		data.setWorkflowId(card.getWorkflowId());
		data.setLaneId(card.getLaneId());
		data.setDescription(card.getDescription());
		data.setUserId(card.getUserId());
		data.setEstimate(card.getEstimate());
		data.setNotes(card.getNotes());
		data.setDeadline(card.getDeadline());
		data.setType(card.getType().toString());

		return data;
	}

	public static List<CardData> transformToData(List<Card> cards) {
		List<CardData> result = new ArrayList<>();
		cards.forEach(x -> result.add(transformToData(x)));
		return result;
	}


}
