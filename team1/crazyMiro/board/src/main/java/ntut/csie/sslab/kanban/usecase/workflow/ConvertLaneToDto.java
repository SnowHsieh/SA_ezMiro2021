package ntut.csie.sslab.kanban.usecase.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.CommittedCard;
import ntut.csie.sslab.kanban.entity.model.workflow.Lane;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertLaneToDto {
    public static LaneDto transform(Lane lane) {
        LaneDto dto= new LaneDto();
        dto.setParentId(lane.getParent().getId());
        dto.setName(lane.getName());
        dto.setLaneId(lane.getId());
        dto.setWorkflowId(lane.getWorkflowId());
        dto.setOrder(lane.getOrder());
        dto.setType(lane.getType().toString());
        dto.setWipLimit(lane.getWipLimit().getValue());
        dto.setLayout(lane.getLayout().toString());
        List<LaneDto> laneDtos = new ArrayList<>();

        for(Lane childLane: lane.getChildren()){
            LaneDto laneDto = transform(childLane);
            laneDtos.add(laneDto);
        }
        dto.setLanes(laneDtos);
        List<CommittedCardDto> committedCardDtos = new ArrayList<>();
        for(CommittedCard committedCard: lane.getCommittedCards()){
            CommittedCardDto committedCardDto = ConvertCommittedCardToDto.transform(committedCard);
            committedCardDtos.add(committedCardDto);
        }
        dto.setCommittedCards(committedCardDtos);
        return dto;
    }
}
