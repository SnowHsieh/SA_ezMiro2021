package ntut.csie.team5.adapter.project;

import ntut.csie.team5.entity.model.project.Project;
import ntut.csie.team5.usecase.project.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {

    private List<Project> projects;

    public ProjectRepositoryImpl() {
        this.projects = new ArrayList<>();
    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public Optional<Project> findById(String id) {
        return projects.stream().filter(project -> project.getProjectId().equals(id)).findAny();

    }

    @Override
    public void save(Project project) {
        projects.add(project);
    }

    @Override
    public void deleteById(String s) {

    }
}
