package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;


import ntut.csie.sslab.kanban.entity.model.workflow.CommittedCard;

import java.util.ArrayList;
import java.util.List;

public class CommittedCardMapper {

    public static CommittedCard transformToCommittedCard(CommittedCardData data) {

        CommittedCard committedCard = new CommittedCard(
                data.getCommittedCardDataId().getCardId(),
                data.getCommittedCardDataId().getLaneId(),
                data.getOrder());

        return committedCard;

    }

    public static List<CommittedCard> transformToCommittedCard(List<CommittedCardData> datas) {

        List<CommittedCard> result = new ArrayList<>();
        datas.forEach( x -> result.add(transformToCommittedCard(x)));
        return result;
    }

    public static CommittedCardData transformToData(CommittedCard committedCard) {

        CommittedCardData committedCardData = new CommittedCardData(
                new CommittedCardDataId(committedCard.getCardId(), committedCard.getLaneId()),
                committedCard.getOrder()
        );

        return committedCardData;
    }


    public static List<CommittedCardData> transformToData(List<CommittedCard> committedCards) {

        List<CommittedCardData> result = new ArrayList<>();
        committedCards.forEach( x -> result.add(transformToData(x)));
        return result;
    }

}
