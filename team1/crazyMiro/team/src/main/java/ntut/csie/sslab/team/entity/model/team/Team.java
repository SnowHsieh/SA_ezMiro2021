package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.team.entity.model.team.event.*;

import java.util.*;

public class Team extends AggregateRoot<TeamId> {

    private TeamName teamName;
    private final Project defaultProject;
    private List<Project> userDefinedProjects;
    private List<TeamMember> teamMembers;

    public Team(TeamId id, String name, String userId) {
        super(id);

        teamName = new TeamName(name);
        teamMembers = new ArrayList<>();
        defaultProject = new Project(new ProjectId(UUID.randomUUID()), id, "Default");
        userDefinedProjects = new ArrayList<>();

        addDomainEvent(new TeamCreated(this.getId(), userId, name));
    }

    public Team(TeamId teamId,
                TeamName teamName,
                Project defaultProject,
                List<Project> userDefinedProjects,
                List<TeamMember> teamMembers) {

        super(teamId);
        this.teamName = teamName;
        this.teamMembers = teamMembers;
        this.defaultProject = defaultProject;
        this.userDefinedProjects = userDefinedProjects;
    }

    public static Team createTeamAndTeamAdminMember(String id, String name, String userId) {
        Team team = new Team(new TeamId(UUID.randomUUID()), name, userId);
        team.addMember(userId, Role.TeamAdmin);
        return team;
    }

    public ProjectId createProject(String name) {

        Project project = new Project(new ProjectId(UUID.randomUUID()), this.getId(), name);
        userDefinedProjects.add(project);
        addDomainEvent(new ProjectCreated(id, project.getId(), name));
        return project.getId();

    }

    public void deleteProject(ProjectId projectId) {

        for (Iterator<Project> iterator = userDefinedProjects.iterator(); iterator.hasNext();) {
            Project each = iterator.next();
            if (each.getId().equals(projectId)){
                iterator.remove();
                addDomainEvent(new ProjectDeleted(id, projectId));
                return;
            }
        }
    }

    public void commitBoard(BoardId boardId, String boardName) {
        Board board = new Board(boardId, this.getId(), defaultProject.getId(), boardName);
        defaultProject.addBoard(board);
        addDomainEvent(new BoardCommittedToTeam(id, boardId, boardName));
    }

    public void uncommitBoard(BoardId boardId) {

        for (Iterator<Project> itr = getAllProjects().iterator(); itr.hasNext();) {
            Project project = itr.next();
            for(Board board : project.getBoards()){
                if (board.getId().equals(boardId)){
                    project.removeBoard(board);
                    addDomainEvent(new BoardRemovedFromProject(id, boardId, project.getId(), board.getName()));
                    addDomainEvent(new BoardUncommittedFromTeam(id, boardId, board.getName()));
                    return;
                }
            }
        }
        throw new RuntimeException("Board not found, board id = " + boardId);
    }

    private Board getBoard(BoardId boardId){
        for (Board each : getAllBoards()) {
            if (each.getId().equals(boardId))
                return each;
        }
        throw new RuntimeException("Board not found, board id = " + boardId);
    }


    public Project getProject(ProjectId projectId){
        for (Project each : getAllProjects()) {
            if (each.getId().equals(projectId))
                return each;
        }

        throw new RuntimeException("Project not found, project id = " + projectId.value());
    }

    public void moveBoardToProject(BoardId boardId, ProjectId projectId) {

        Board board = getBoard(boardId);
        if(board.getProjectId().equals(projectId)) {
            return;
        }

        Project toProject = getProject(projectId);
        Project fromProject = getProject(board.getProjectId());

        fromProject.removeBoard(board);
        addDomainEvent(new BoardRemovedFromProject(id, boardId, fromProject.getId(), board.getName()));

        board.setProjectId(toProject.getId());
        toProject.addBoard(board);
        addDomainEvent(new BoardMovedToProject(id, boardId, projectId, board.getName()));
    }

    private List<Project> getAllProjects(){
        List<Project> allProjects = new ArrayList<>();
        allProjects.add(defaultProject);
        allProjects.addAll(userDefinedProjects);
        return allProjects;
    }

    private List<Board> getAllBoards(){
        List<Board> allBoards = new ArrayList<>();
        getAllProjects().stream().forEach( x -> allBoards.addAll(x.getBoards()));
        return allBoards;
    }

    public ProjectId renameProject(ProjectId projectId, String newName) {

        Project project = getProject(projectId);
        if(!project.getName().equals(newName)) {
            String oldName = project.getName();
            project.setName(newName);
            addDomainEvent(new ProjectRenamed(id, projectId, oldName, newName));
        }
        return project.getId();
    }

    public void addMember(String userId, Role role) {
        for(TeamMember teamMember : teamMembers) {
            if(teamMember.getUserId().equals(userId) &&
                    teamMember.getRole().equals(role)) {
                return;
            }
        }

        teamMembers.add(new TeamMember(userId, getId(), role));
        addDomainEvent(new TeamMemberAdded(userId, getId(), role.toString()));
    }

    public List<Project> getUserDefinedProjects() {
        return Collections.unmodifiableList(userDefinedProjects);
    }

    public List<Board> getBoards() {
        return defaultProject.getBoards();
    }

    public Project getDefaultProject() {
        return defaultProject;
    }

    public List<TeamMember> getTeamMembers() { return teamMembers; }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public TeamMember getTeamMemberById(String userId) {

        for(TeamMember each : teamMembers){
            if (each.getUserId().equalsIgnoreCase(userId))
                return each;
        }
        return null;
    }

    public List<Board> getBoardsByProjectId(ProjectId projectId) {

        List<Board> result = new ArrayList<>();
        for(Project each : userDefinedProjects){
            if (each.getId().equals(projectId)){
                result.addAll(each.getBoards());
                break;
            }
        }

        return Collections.unmodifiableList(result);
    }

    public void renameBoard(BoardId boardId, String newName) {
        for(Board each : getAllBoards()){
            if (each.getId().equals(boardId)) {
                String oldName = each.getName();
                each.setName(newName);
                addDomainEvent(new CommittedBoardRenamed(id, boardId, oldName, newName));
                break;
            }
        }
    }

    public void markAsRemoved() {
        addDomainEvent(new TeamDeleted(id));
    }

    public String getName(){
        return teamName.value();
    }

}
