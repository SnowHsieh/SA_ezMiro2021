package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.*;

import java.util.ArrayList;
import java.util.List;

public class LaneMapper {

    public static LaneData transformToLaneData(Lane lane) {
        return transformToLaneData(lane, null);

    }

    private static LaneData transformToLaneData(Lane lane, LaneData parent) {

        LaneData laneData = new LaneData(
                lane.getId(),
                lane.getWorkflowId(),
                parent,
                lane.getName(),
                lane.getWipLimit().getValue(),
                lane.getOrder(),
                lane.getType().name(),
                lane.getLayout().name()
        );

        for(Lane _lane : lane.getChildren()) {
            laneData.addChild(LaneMapper.transformToLaneData(_lane, laneData));
        }

        CommittedCardMapper.transformToData(lane.getCommittedCards()).forEach(laneData::addCommittedCardData);

        return laneData;
    }

    public static Lane transformToModel(LaneData laneData) {
        return transformToModel(laneData, NullLane.newInstance());
    }

    private static Lane transformToModel(LaneData laneData, Lane parent) {
        Lane lane;

        if (laneData.isStage()) {
            lane = new Stage(
                    laneData.getId(),
                    laneData.getWorkflowId(),
                    parent,
                    laneData.getName(),
                    new WipLimit(laneData.getWipLimit()),
                    laneData.getOrder(),
                    LaneType.valueOf(laneData.getType()));
        } else {
            lane = new SwimLane(
                    laneData.getId(),
                    laneData.getWorkflowId(),
                    parent,
                    laneData.getName(),
                    new WipLimit(laneData.getWipLimit()),
                    laneData.getOrder(),
                    LaneType.valueOf(laneData.getType()));
        }

        List<Lane> children = new ArrayList<>();
        for (LaneData _laneData : laneData.getChildren()) {
            children.add(transformToModel(_laneData, lane));
        }
        lane.addChildren(children);

        for(CommittedCardData committedCardData : laneData.getCommittedCardData()) {
            lane.addCommittedCards(CommittedCardMapper.transformToCommittedCard(committedCardData));
        }

        return lane;
    }
}
