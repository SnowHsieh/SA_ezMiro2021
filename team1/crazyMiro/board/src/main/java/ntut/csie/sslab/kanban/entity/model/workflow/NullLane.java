package ntut.csie.sslab.kanban.entity.model.workflow;

public final class NullLane extends Lane {

    public final static String ID = "-1";

    private NullLane() {
        super(ID, "-1", null, "Null Lane", new WipLimit(-1), 0, LaneType.Standard);
        setLayout(LaneLayout.Vertical);
    }

    public static NullLane newInstance() {
        return new NullLane();
    }
}
