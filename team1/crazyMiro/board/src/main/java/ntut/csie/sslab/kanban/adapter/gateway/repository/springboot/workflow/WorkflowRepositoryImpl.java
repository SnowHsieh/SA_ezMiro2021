package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import com.google.common.collect.Lists;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

import java.util.List;
import java.util.Optional;

public class WorkflowRepositoryImpl implements WorkflowRepository {

    private WorkflowRepositoryPeer peer;

    public WorkflowRepositoryImpl(WorkflowRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public List<Workflow> findAll() {
        List<WorkflowData> results = Lists.newArrayList(peer.findAll());
        return WorkflowMapper.transformToDomain(results);
    }

    @Override
    public Optional<Workflow> findById(String id) {
        Workflow workflow = null;

        Optional<WorkflowData> data = peer.findById(id);
        if (data.isPresent()){
            workflow = WorkflowMapper.transformToDomain(data.get());
        }

        return Optional.ofNullable(workflow);
    }

    @Override
    public void save(Workflow workflow) {
        WorkflowData workflowData = WorkflowMapper.transformToData(workflow);
        peer.save(workflowData);
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }

    @Override
    public List<Workflow> getWorkflowsByBoardId(String boardId) {
        List<WorkflowData> results = peer.getWorkflowsByBoardId(boardId);
        return WorkflowMapper.transformToDomain(results);
    }
}
