package ntut.csie.sslab.team.entity.model.team;


import ntut.csie.sslab.team.entity.model.team.event.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TeamTest {

    private String userId = "userId";
    private ProjectId projectId;
    private String projectName;
    private BoardId boardId = BoardId.valueOf("boardId");
    private String boardName;
    private Team team;
    private String teamName = "kanbanTeam";

    @BeforeEach
    public void setUp(){
        should_publish_team_created_domain_event();
    }

    private void should_publish_team_created_domain_event() {
        Assertions.assertNull(team);
        team = TeamBuilder.newInstance()
                .teamName(teamName)
                .userId(userId)
                .build();
        Assertions.assertNotNull(team);
        assertEquals(teamName, team.getName());
        assertEquals(1, team.getTeamMembers().size());
        assertEquals(userId, team.getTeamMembers().get(0).getUserId());
        assertEquals(Role.TeamAdmin, team.getTeamMembers().get(0).getRole());
        assertEquals(team.getId(), team.getTeamMembers().get(0).getTeamId());


        assertEquals(2, team.getDomainEvents().size());
        assertEquals(TeamCreated.class, team.getDomainEvents().get(0).getClass());
        TeamCreated teamCreated = (TeamCreated)team.getDomainEvents().get(0);
        assertEquals(team.getId(), teamCreated.teamId());
        assertEquals(team.getName(), teamCreated.teamName());
        assertEquals(team.getTeamMembers().get(0).getUserId(), teamCreated.userId());


        assertEquals(TeamMemberAdded.class, team.getDomainEvents().get(1).getClass());
        TeamMemberAdded teamMemberAdded = (TeamMemberAdded)team.getDomainEvents().get(1);
        assertEquals(userId, teamMemberAdded.userId());
        assertEquals(team.getId(), teamMemberAdded.teamId());
        assertEquals(Role.TeamAdmin.toString(), teamMemberAdded.role());
    }

    @Test
    public void should_publish_project_created_domain_event_when_create_project() {
        assertEquals(0, team.getUserDefinedProjects().size());
        projectName = "ezKanban";
        projectId = team.createProject(projectName);
        assertEquals(1, team.getUserDefinedProjects().size());

        Project project = team.getProject(projectId);
        assertEquals(projectId, project.getId());
        assertEquals(projectName, project.getName());

        assertEquals(3, team.getDomainEvents().size());
        assertEquals(ProjectCreated.class, team.getDomainEvents().get(2).getClass());
        assertEquals(team.getId(), ((ProjectCreated)team.getDomainEvents().get(2)).teamId());
        assertEquals(projectId, ((ProjectCreated)team.getDomainEvents().get(2)).projectId());
        assertEquals(projectName, ((ProjectCreated)team.getDomainEvents().get(2)).name());
    }

    @Test
    public void should_publish_board_committed_to_team_domain_event_when_commit_board() {
        assertEquals(0, team.getBoards().size());
        boardId = BoardId.valueOf("boardId");
        boardName = "Kanban Board";
        team.commitBoard(boardId, boardName);
        assertEquals(1, team.getBoards().size());

        Board board = team.getBoards().get(0);
        assertEquals(boardId, board.getId());
        assertEquals(boardName, board.getName());

        assertEquals(3, team.getDomainEvents().size());
        assertEquals(BoardCommittedToTeam.class, team.getDomainEvents().get(2).getClass());
        BoardCommittedToTeam boardCommittedToTeam = ((BoardCommittedToTeam)team.getDomainEvents().get(2));
        assertEquals(team.getId(), boardCommittedToTeam.teamId());
        assertEquals(boardId, boardCommittedToTeam.boardId());
        assertEquals(boardName, boardCommittedToTeam.name());
    }

    @Test
    public void should_publish_board_removed_from_project_and_board_uncommitted_to_team_domain_event_when_uncommit_board() {
        should_publish_board_committed_to_team_domain_event_when_commit_board();
        team.clearDomainEvents();

        team.uncommitBoard(boardId);
        assertEquals(0, team.getBoards().size());

        assertEquals(2, team.getDomainEvents().size());

        assertEquals(BoardRemovedFromProject.class, team.getDomainEvents().get(0).getClass());
        assertEquals(BoardUncommittedFromTeam.class, team.getDomainEvents().get(1).getClass());

        BoardUncommittedFromTeam boardUncommittedFromTeam = ((BoardUncommittedFromTeam)team.getDomainEvents().get(1));
        assertEquals(team.getId(), boardUncommittedFromTeam.teamId());
        assertEquals(boardId, boardUncommittedFromTeam.boardId());
        assertEquals(boardName, boardUncommittedFromTeam.boardName());
    }



    @Test
    public void should_publish_board_uncommitted_to_team_domain_event_when_uncommit_board_which_is_in_project() {
        team.clearDomainEvents();
        projectId = team.createProject(projectName);
        team.commitBoard(boardId, boardName);
        team.moveBoardToProject(boardId, projectId);
        team.clearDomainEvents();

        team.uncommitBoard(boardId);
        Project project = team.getProject(projectId);
        assertEquals(0, project.getBoards().size());
        assertEquals(0, team.getBoards().size());

        assertEquals(2, team.getDomainEvents().size());
        assertEquals(BoardRemovedFromProject.class, team.getDomainEvents().get(0).getClass());
        assertEquals(BoardUncommittedFromTeam.class, team.getDomainEvents().get(1).getClass());

        BoardRemovedFromProject boardRemovedFromProject = ((BoardRemovedFromProject)team.getDomainEvents().get(0));
        assertEquals(team.getId(), boardRemovedFromProject.teamId());
        assertEquals(boardId, boardRemovedFromProject.boardId());
        assertEquals(projectId, boardRemovedFromProject.projectId());
        assertEquals(boardName, boardRemovedFromProject.boardName());

        assertEquals(BoardUncommittedFromTeam.class, team.getDomainEvents().get(1).getClass());
        BoardUncommittedFromTeam boardUncommittedFromTeam = ((BoardUncommittedFromTeam)team.getDomainEvents().get(1));
        assertEquals(team.getId(), boardUncommittedFromTeam.teamId());
        assertEquals(boardId, boardUncommittedFromTeam.boardId());
        assertEquals(boardName, boardUncommittedFromTeam.boardName());
    }



    @Test
    public void should_publish_project_deleted_domain_event_when_delete_project() {
        should_publish_project_created_domain_event_when_create_project();
        team.clearDomainEvents();
        assertEquals(1, team.getUserDefinedProjects().size());

        projectId = team.getUserDefinedProjects().get(0).getId();
        String projectName = team.getUserDefinedProjects().get(0).getName();
        team.deleteProject(projectId);

        assertEquals(0, team.getUserDefinedProjects().size());
        assertEquals(1, team.getDomainEvents().size());
        assertEquals(ProjectDeleted.class, team.getDomainEvents().get(0).getClass());
        assertEquals(team.getId(), ((ProjectDeleted)team.getDomainEvents().get(0)).teamId());
        assertEquals(projectId, ((ProjectDeleted)team.getDomainEvents().get(0)).projectId());
    }

    @Test
    public void should_publish_team_deleted_domain_event_when_delete_team() {
        team.clearDomainEvents();
        team.markAsRemoved();

        assertEquals(1,team.getDomainEvents().size());
        assertEquals(TeamDeleted.class, team.getDomainEvents().get(0).getClass());
        TeamDeleted teamDeleted = (TeamDeleted)team.getDomainEvents().get(0);
        assertEquals(team.getId(), teamDeleted.teamId());
    }

}
