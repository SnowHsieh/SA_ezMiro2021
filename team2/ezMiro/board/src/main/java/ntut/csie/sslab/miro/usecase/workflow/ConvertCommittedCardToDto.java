package ntut.csie.sslab.miro.usecase.workflow;

import ntut.csie.sslab.miro.entity.model.workflow.CommittedCard;

public class ConvertCommittedCardToDto {
    public static CommittedCardDto transform(CommittedCard committedCard){
        CommittedCardDto dto= new CommittedCardDto();
        dto.setCardId(committedCard.getCardId());
        dto.setLaneId(committedCard.getLaneId());
        dto.setOrder(committedCard.getOrder());
        return dto;
    }
}
