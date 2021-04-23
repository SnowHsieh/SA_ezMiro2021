package ntut.csie.team5.entity.model.project;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.project.event.BoardCommitted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Project extends AggregateRoot<String> {

    private String name;
    private String teamId;
    private List<CommittedBoard> committedBoards;

    public Project(String projectId, String name, String teamId) {
        super(projectId);
        this.name = name;
        this.teamId = teamId;
        this.committedBoards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getProjectId() {
        return getId();
    }

    public void commitBoard(String boardId) {
        addBoard(boardId);

        addDomainEvent(new BoardCommitted(boardId, getProjectId()));
    }

    private void addBoard(String boardId) {
        committedBoards.add(new CommittedBoard(boardId, getProjectId()));
    }

    public List<CommittedBoard> getCommittedBoards() {
        return committedBoards;
    }
}
