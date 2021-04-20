package ntut.csie.sslab.kanban.entity.model.workflow;

public class Stage extends Lane {

    public Stage(
            String id,
            String workflowId,
            Lane parent,
            String name,
            WipLimit wipLimit,
            int order,
            LaneType type) {

        super(id, workflowId, parent, name, wipLimit, order, type);
        setLayout(LaneLayout.Vertical);
    }
}
