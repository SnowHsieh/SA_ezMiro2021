package ntut.csie.sslab.kanban.entity.model.workflow;

import ntut.csie.sslab.ddd.model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Lane extends Entity<String> {
    private String workflowId;
    private String name;
    private Lane parent;
    private WipLimit wipLimit;
    private int order;
    private LaneType type;
    private final List<Lane> children;
    private final List<CommittedCard> committedCards;
    private LaneLayout layout;

    public Lane(String laneId, String workflowId, Lane parent, String name, WipLimit wipLimit, int order, LaneType type) {
        super(laneId);
        this.name = name;
        this.workflowId = workflowId;
        this.parent = parent;
        this.wipLimit = wipLimit;
        this.order = order;
        this.type = type;
        this.children = new ArrayList<>();
        this.committedCards = new ArrayList<>();
    }

    public void removeLanes(){
        children.forEach(x -> x.removeLanes());
        children.clear();
    }

    public void addCard(String cardId) {
        committedCards.add(new CommittedCard(cardId, getId(), committedCards.size()));
    }

    // order starts from 0
    public void addCard(String cardId, int order) {
        committedCards.add(order, new CommittedCard(cardId, getId(), order));

        for(int i = 0; i < committedCards.size(); i++) {
            committedCards.get(i).setOrder(i);
        }
    }

    public Optional<CommittedCard> getCardById(String cardId){

    return committedCards
            .parallelStream()
            .filter( x -> cardId.equals(x.getCardId()))
            .findFirst();
}

    public void removeCard(String cardId) {

        for (CommittedCard committedCard : committedCards) {
            if (committedCard.getCardId().equals(cardId)) {
                committedCards.remove(committedCard);
                for (int i = 0; i < committedCards.size(); i++) {
                    committedCards.get(i).setOrder(i);
                }
                break;
            }
        }
    }

    public Optional<Lane> getLaneById(Lane lane, String laneId) {
        if (lane.getId().equals(laneId)) {
            return Optional.of(lane);
        }

        for (Lane each : lane.getChildren()){
            Optional<Lane> targetLane = getLaneById(each, laneId);
            if (targetLane.isPresent()){
                return targetLane;
            }
        }

        return Optional.empty();
    }

    public boolean rename(String newName) {

        if(!getName().equals(newName)) {
            setName(newName);
            return true;
        }
        return false;
    }

    public boolean changeWipLimit(WipLimit newWipLimit) {

        if (wipLimit.getValue() != newWipLimit.getValue()) {
            wipLimit = newWipLimit;
            return true;
        }
        return false;
    }

    public Lane createStage(String name, int wipLimit, LaneType type) {

        Lane stage = LaneBuilder.newInstance()
                .workflowId(workflowId)
                .parent(this)
                .name(name)
                .order(children.size())
                .wipLimit(wipLimit)
                .type(type)
                .stage()
                .build();

        this.children.add(stage);

        return stage;
    }

    public Lane createSwimLane(String name, int wipLimit, LaneType type) {

        Lane swimLane = LaneBuilder.newInstance()
                .parent(this)
                .workflowId(workflowId)
                .name(name)
                .order(children.size())
                .wipLimit(wipLimit)
                .type(type)
                .swimLane()
                .build();

        this.children.add(swimLane);
        return swimLane;
    }

    public boolean isRoot(){
        return parent.getId().equals(NullLane.ID);
    }

    public void moveBackward(){
        order++;
    }

    public void moveForward(){
        order--;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public Lane getParent() {
        return parent;
    }

    public WipLimit getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(WipLimit wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public LaneType getType() {
        return type;
    }

    public void setType(LaneType type) {
        this.type = type;
    }

    public List<Lane> getChildren() {
        return children;
    }

    public void addChildren(List<Lane> children) {
        this.children.addAll(children);
    }

    public List<CommittedCard> getCommittedCards() {
        return committedCards;
    }

    public void addCommittedCards(CommittedCard committedCard) {
        this.committedCards.add(committedCard);
    }


    public LaneLayout getLayout() {
        return layout;
    }

    protected void setLayout(LaneLayout layout) {
        this.layout = layout;
    }

    public boolean isStage(){
        return getLayout() == LaneLayout.Vertical;
    }
}
