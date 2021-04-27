//package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workspace;
//
//import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
//import ntut.csie.sslab.kanban.usecase.workspace.WorkspaceRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class WorkspaceRepositoryImpl implements WorkspaceRepository {
//
//    private List<Workspace> workspaces;
//
//    public WorkspaceRepositoryImpl() {
//        this.workspaces = new ArrayList<>();
//    }
//
//    @Override
//    public List<Workspace> findAll() {
//        return workspaces;
//    }
//
//    @Override
//    public Optional<Workspace> findById(String id) {
//        return workspaces.stream().filter(x-> x.getWorkspaceId().equals(id)).findFirst();
//    }
//
//    @Override
//    public void save(Workspace workspace) {
//        workspaces.add(workspace);
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//}
//
