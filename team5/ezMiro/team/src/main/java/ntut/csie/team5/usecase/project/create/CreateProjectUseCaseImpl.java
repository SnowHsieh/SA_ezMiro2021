package ntut.csie.team5.usecase.project.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.project.Project;
import ntut.csie.team5.entity.model.project.ProjectBuilder;
import ntut.csie.team5.usecase.project.ProjectRepository;

public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    private ProjectRepository projectRepository;
    private DomainEventBus domainEventBus;

    public CreateProjectUseCaseImpl(ProjectRepository projectRepository, DomainEventBus domainEventBus) {
        this.projectRepository = projectRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public CreateProjectInput newInput() {
        return new CreateProjectInputImpl();
    }

    @Override
    public void execute(CreateProjectInput input, CqrsCommandOutput output) {
        Project project = ProjectBuilder.newInstance()
                .name(input.getProjectName())
                .teamId(input.getTeamId())
                .build();

        projectRepository.save(project);
        domainEventBus.postAll(project);

        output.setId(project.getProjectId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class CreateProjectInputImpl implements CreateProjectInput {

        private String teamId;
        private String projectName;

        @Override
        public String getTeamId() {
            return teamId;
        }

        @Override
        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        @Override
        public String getProjectName() {
            return projectName;
        }

        @Override
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }
}
