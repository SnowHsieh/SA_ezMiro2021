package ntut.csie.sslab.team.entity.model.team;

import org.junit.jupiter.api.*;

@Disabled("Disabled until Project entity is developed")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProjectTest {

    private String teamId = "teamId";
    private Project project;

    @BeforeEach
    public void setUp() {
        should_publish_project_created_domain_event_when_create_project();
    }

    private void should_publish_project_created_domain_event_when_create_project() {
//        assertNull(project);
//        project = ProjectBuilder.newInstance()
//                .teamId(teamId)
//                .title("project")
//                .build();
//        assertNotNull(project);
//        assertEquals(teamId, project.getTeamId());
//        assertEquals("project", project.getTitle());
//
//        assertEquals(1, project.getDomainEvents().size());
//        assertEquals(ProjectCreated.class.getSimpleName(), project.getDomainEvents().get(0).getClass().getSimpleName());
//        assertEquals(teamId, ((ProjectCreated)project.getDomainEvents().get(0)).teamId());
//        assertEquals(project.getProjectId(), ((ProjectCreated)project.getDomainEvents().get(0)).projectId());
//        assertEquals(project.getTitle(), ((ProjectCreated)project.getDomainEvents().get(0)).title());
    }

    @Test
    public void should_publish_board_committed_domain_event_when_commit_board() {
//        assertEquals(0, project.getCommittedBoards().size());
//        String boardId = "boardId";
//        project.commitBoardInProject(boardId);
//        assertEquals(1, project.getCommittedBoards().size());
//
//        assertEquals(2, project.getDomainEvents().size());
//        assertEquals(BoardCommittedToTeam.class.getSimpleName(), project.getDomainEvents().get(1).getClass().getSimpleName());
//        assertEquals(boardId, ((BoardCommittedToTeam)project.getDomainEvents().get(1)).boardId());
//        assertEquals(project.getProjectId(), ((BoardCommittedToTeam)project.getDomainEvents().get(1)).projectId());
//
//        CommittedBoard committedBoard =  project.getCommittedBoards().get(0);
//        assertEquals(boardId, committedBoard.getBoardId());
//        assertEquals(0, committedBoard.getOrder());
    }


    @Test
    public void should_publish_board_uncommitted_domain_event_when_uncommit_board() {
//        should_publish_board_committed_domain_event_when_commit_board();
//        assertEquals(1, project.getCommittedBoards().size());
//
//        String boardId = project.getCommittedBoards().get(0).getBoardId();
//        project.uncommittedBoard(boardId);
//        assertEquals(0, project.getCommittedBoards().size());
//
//        assertEquals(3, project.getDomainEvents().size());
//        assertEquals(BoardUncommittedFromTeam.class.getSimpleName(), project.getDomainEvents().get(2).getClass().getSimpleName());
//        assertEquals(boardId, ((BoardUncommittedFromTeam)project.getDomainEvents().get(2)).boardId());
//        assertEquals(project.getProjectId(), ((BoardUncommittedFromTeam)project.getDomainEvents().get(2)).projectId());
    }

    @Test
    public void should_publish_project_title_changed_domain_event_when_change_project_title() {
//        String oldTitle = project.getTitle();
//        project.changeTitle("newProject");
//
//        assertEquals("newProject", project.getTitle());
//        assertEquals(2, project.getDomainEvents().size());
//        assertEquals(ProjectTitleChanged.class.getSimpleName(), project.getDomainEvents().get(1).getClass().getSimpleName());
//        assertEquals(oldTitle, ((ProjectTitleChanged)project.getDomainEvents().get(1)).oldTitle());
//        assertEquals(project.getTitle(), ((ProjectTitleChanged)project.getDomainEvents().get(1)).newTitle());
//        assertEquals(project.getProjectId(), ((ProjectTitleChanged)project.getDomainEvents().get(1)).projectId());
    }

    @Test
    public void should_publish_project_deleted_domain_event_when_delete_project() {
//        project.markAsRemove();
//
//        assertEquals(2, project.getDomainEvents().size());
//        assertEquals(ProjectDeleted.class.getSimpleName(), project.getDomainEvents().get(1).getClass().getSimpleName());
//        assertEquals(teamId, ((ProjectDeleted)project.getDomainEvents().get(1)).teamId());
//        assertEquals(project.getProjectId(), ((ProjectDeleted)project.getDomainEvents().get(1)).projectId());
    }
}
