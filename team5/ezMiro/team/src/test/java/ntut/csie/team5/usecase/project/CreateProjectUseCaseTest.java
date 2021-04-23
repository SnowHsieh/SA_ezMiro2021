package ntut.csie.team5.usecase.project;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.project.ProjectRepositoryImpl;
import ntut.csie.team5.entity.model.project.Project;
import ntut.csie.team5.usecase.project.create.CreateProjectInput;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCase;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateProjectUseCaseTest {

    private ProjectRepository projectRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        projectRepository = new ProjectRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_create_project() {
        CreateProjectUseCase createProjectUseCase = new CreateProjectUseCaseImpl(projectRepository, domainEventBus);
        CreateProjectInput createProjectInput = createProjectUseCase.newInput();
        CqrsCommandPresenter createProjectOutput = CqrsCommandPresenter.newInstance();

        createProjectInput.setTeamId("1");
        createProjectInput.setProjectName("Project Name");

        createProjectUseCase.execute(createProjectInput, createProjectOutput);

        assertNotNull(createProjectOutput.getId());
        assertEquals(ExitCode.SUCCESS, createProjectOutput.getExitCode());

        Project project = projectRepository.findById(createProjectOutput.getId()).get();
        assertEquals(createProjectOutput.getId(), project.getProjectId());
        assertEquals("1", project.getTeamId());
        assertEquals("Project Name", project.getName());
    }
}
