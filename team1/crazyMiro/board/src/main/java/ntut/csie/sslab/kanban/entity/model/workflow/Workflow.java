package ntut.csie.sslab.kanban.entity.model.workflow;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.workflow.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Workflow extends AggregateRoot<String> {
    private String boardId;
    private String name;
    private List<Lane> stages;

    public Workflow(String workflowId, String boardId, String name, String userId, String username) {
        super(workflowId);
        this.name = name;
        this.boardId = boardId;
        this.stages = new ArrayList<>();

        addDomainEvent(new WorkflowCreated(workflowId, name, userId, username, boardId));
    }

    public void addStage(Stage stage) {
        stages.add(stage);
    }

    public String createStage(String name, int wipLimit, LaneType laneType, String parentId, String userId, String username, String boardId) {

        if (parentId.equals(NullLane.ID)){
            Lane stage = LaneBuilder.newInstance()
                    .workflowId(getWorkflowId())
                    .parent(NullLane.newInstance())
                    .name(name)
                    .wipLimit(wipLimit)
                    .type(laneType)
                    .stage()
                    .order(stages.size())
                    .build();

            stages.add(stage);

            addDomainEvent(new StageCreated(getWorkflowId(), stage.getParent().getId(), stage.getId(), name, laneType.toString(), wipLimit, userId, username, boardId));
            return stage.getId();
        }

        Lane parentLane = getLaneById(parentId).orElseThrow(() -> new RuntimeException("Parent lane not found, id = " + parentId));
        String stageId = parentLane.createStage(name, wipLimit, laneType).getId();
        addDomainEvent(new StageCreated(getWorkflowId(), parentId, stageId, name, laneType.toString(), wipLimit, userId, username, boardId));

        return stageId;
    }

    public String createSwimLane(String parentId, String name, int wipLimit, LaneType type, String userId, String username, String boardId) {
        Lane lane = getLaneById(parentId).orElseThrow(() -> new RuntimeException("Parent lane not found, id = " + parentId));
        Lane swimLane = lane.createSwimLane(name, wipLimit, type);

        addDomainEvent(new SwimLaneCreated(getWorkflowId(), parentId,  swimLane.getId(), name, type.toString(), wipLimit, userId, username, boardId));
        return swimLane.getId();
    }

    public Optional<Lane> getLaneById(String laneId) {

        Optional<Lane> targetLane;
        for (Lane stage: stages) {
            targetLane = stage.getLaneById(stage, laneId);
            if(targetLane.isPresent()) {
                return targetLane;
            }
        }

        return Optional.empty();

//        this does not work
//        return stages.parallelStream()
//                .filter(stage ->
//                        stage.getLaneById(stage, laneId).isPresent())
//                .findFirst();
    }


    public void rename (String newName, String userId, String username, String boardId){
        if(!(getName().equals(newName))){
            String oldName = getName();
            setName(newName);
            addDomainEvent(new WorkflowRenamed(getWorkflowId(), oldName, newName, userId, username, boardId));
        }
    }

    public String deleteLaneById(String laneId, String userId, String username, String boardId) {

        Lane targetLane = getLaneById(laneId).orElseThrow(() -> new RuntimeException("Lane not found, id = " + laneId));

        if (targetLane.getParent().getId().equals(NullLane.ID))
            return deleteRootLaneById(targetLane, userId, username, boardId);
        else
            return deleteSubLaneById(targetLane, userId, username, boardId);
    }


    private String deleteRootLaneById(Lane targetLane, String userId, String username, String boardId) {

        if (targetLane.getCommittedCards().size() != 0) {
            throw new RuntimeException("There are "
                    + targetLane.getCommittedCards().size()
                    + " cards in lane, you can't delete it.");
        }

        for (Lane each : stages) {
            if (each.getId().equals(targetLane.getId())){
                removeAllLanes(targetLane, userId, username, boardId);
                stages.remove(each);
                addDomainEvent(new StageDeleted(getWorkflowId(),
                                NullLane.ID,
                                targetLane.getId(),
                                targetLane.getName(),
                                userId,
                                username,
                                boardId));
                break;
            }
        }

        for (int i = 0; i < stages.size() ; i++) {
            stages.get(i).setOrder(i - 1);
        }

        return targetLane.getId();
    }

    private String deleteSubLaneById(Lane targetLane, String userId, String username, String boardId) {

        if (targetLane.getCommittedCards().size() != 0) {
            throw new RuntimeException("There are "
                    + targetLane.getCommittedCards().size()
                    + " cards in lane, you cannot delete it.");
        }

        Lane parentLane = getLaneById(targetLane.getParent().getId()).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + targetLane.getParent().getId()));

        removeAllLanes(targetLane, userId, username, boardId);
        targetLane.removeLanes();

        parentLane.getChildren().remove(targetLane);

        if (targetLane.isStage()) {
            addDomainEvent(new StageDeleted(getWorkflowId(),
                    targetLane.getParent().getId(),
                    targetLane.getId(),
                    targetLane.getName(),
                    userId,
                    username,
                    boardId));
        }
        else {
            addDomainEvent(new SwimLaneDeleted(getWorkflowId(),
                    targetLane.getParent().getId(),
                    targetLane.getId(),
                    targetLane.getName(),
                    userId,
                    username,
                    boardId));
        }

        for (int j = 0; j< parentLane.getChildren().size(); j++) {
            parentLane.getChildren().get(j).setOrder(j);
        }

        return targetLane.getId();

    }

    public void commitCardInLane(String cardId, String newLaneId, int order, String userId, String username, String boardId) {

        Lane lane = getLaneById(newLaneId).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + newLaneId));

        lane.addCard(cardId, order);

        addDomainEvent(new CardCommitted(
                cardId,
                getWorkflowId(),
                newLaneId,
                order,
                userId,
                username,
                boardId));
    }

    public void uncommitCardInLane(String cardId, String laneId, String userId, String username, String boardId) {
        Lane lane = getLaneById(laneId).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + laneId));

        lane.removeCard(cardId);

        addDomainEvent(new CardUncommitted(
                cardId,
                getWorkflowId(),
                laneId,
                userId,
                username,
                boardId
        ));
    }

    public synchronized void moveStage(String stageId, int newOrdering, String userId, String userName, String boardId) {

        Lane lane = this.getLaneById(stageId).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + stageId));

        int oldOrdering = lane.getOrder();

        if(isMoveToSameOrdering(newOrdering, oldOrdering))
            return;

        for(Lane each : stages){
            if(each.getOrder() == oldOrdering){
                each.setOrder(newOrdering);
                continue;
            }
            if (isMoveForward(newOrdering, oldOrdering)){
                if (inForwardFreeZone(newOrdering, oldOrdering, each))
                    continue;
                if(inForwardAffectedZone(newOrdering, oldOrdering, each))
                    each.moveBackward();
            }else {
                // move backward
                if (inBackwardFreeZone(newOrdering, oldOrdering, each)){
                    continue;
                }
                if(inBackwardAffectedZone(newOrdering, oldOrdering, each)){
                    each.moveForward();
                }
            }
        }

        addDomainEvent(new StageMoved(getWorkflowId(), stageId, oldOrdering, newOrdering, lane.getName(), userId, userName, boardId));
    }


    private boolean inForwardAffectedZone(int newOrdering, int oldOrdering, Lane each){
        return each.getOrder() >= newOrdering && each.getOrder() < oldOrdering;
    }

    private boolean inBackwardAffectedZone(int newOrdering, int oldOrdering, Lane each){
        return each.getOrder() > oldOrdering && each.getOrder() <= newOrdering;
    }


    private boolean inForwardFreeZone(int newOrdering, int oldOrdering, Lane each) {
        return each.getOrder() < newOrdering || each.getOrder() > oldOrdering;
    }

    private boolean inBackwardFreeZone(int newOrdering, int oldOrdering, Lane each){
        return (each.getOrder() < oldOrdering || each.getOrder() > newOrdering);
    }

    private boolean isMoveToSameOrdering(int newOrdering, int oldOrdering) {
        return newOrdering == oldOrdering;
    }

    private boolean isMoveForward(int newOrdering, int oldOrdering){
        return newOrdering < oldOrdering ? true : false;
    }



    public void markAsRemove(String userId, String username, String boardId) {
        for(int i = 0; i != stages.size();){
            removeAllLanes(stages.get(0), userId, username, boardId);
            addDomainEvent(new StageDeleted(getWorkflowId(),
                                            stages.get(0).getParent().getId(),
                                            stages.get(0).getId(),
                                            stages.get(0).getName(),
                                            userId,
                                            username,
                                            boardId));
            stages.remove(0);
        }
        addDomainEvent(new WorkflowDeleted(getWorkflowId(), getName(), userId, username, boardId));
    }

    public void removeAllLanes(Lane lane, String userId, String username, String boardId) {

        for (Lane each : lane.getChildren()) {
            removeAllLanes(each, userId, username, boardId);

            if (each instanceof Stage) {
                addDomainEvent(new StageDeleted(
                        getWorkflowId(),
                        each.getParent().getId(),
                        each.getId(),
                        each.getName(),
                        userId,
                        username,
                        boardId));
            }
            else if (each instanceof SwimLane) {
                addDomainEvent(new SwimLaneDeleted(
                        getWorkflowId(),
                        each.getParent().getId(),
                        each.getId(),
                        each.getName(),
                        userId,
                        username,
                        boardId));
            }
        }
        lane.getChildren().forEach(x -> x.removeLanes());
    }

    public void renameLane(String laneId, String newName, String userId, String username) {
        Lane lane = getLaneById(laneId).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + laneId));

        String oldName = lane.getName();

        lane.setName(newName);
        addDomainEvent(new LaneRenamed(getWorkflowId(), laneId, oldName, newName, userId, username, boardId, lane.getLayout().toString()));
    }

    public void setLaneWipLimit(String laneId, int newWipLimit, String userId, String username) {
        Lane lane = getLaneById(laneId).
                orElseThrow(() -> new RuntimeException("Lane not found, id = " + laneId));

        int oldWipLimit = lane.getWipLimit().getValue();
        lane.setWipLimit(new WipLimit(newWipLimit));
        addDomainEvent(new WipLimitSet(getWorkflowId(), laneId, oldWipLimit, newWipLimit, userId, username, boardId, lane.getLayout().toString()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflowId() {
        return getId();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<Lane> getStages() {
        return stages;
    }
}
