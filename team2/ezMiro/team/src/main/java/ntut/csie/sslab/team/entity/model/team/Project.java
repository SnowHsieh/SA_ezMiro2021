package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Project extends Entity<ProjectId> {

    private TeamId teamId;
    private String name;
    private List<Board> boards;


    public Project(ProjectId id, TeamId teamId, String name) {
        super(id);
        this.name = name;
        this.teamId = teamId;
        boards = new ArrayList<>();
    }

    public Optional<Board> findBoardById(BoardId boardId) {
        for(Board each: boards) {
            if(each.getId().equals(boardId)) {
                return Optional.of(each);
            }
        }

        return Optional.empty();
    }

    public void addBoard(Board board){

        if (findBoardById(board.getId()).isPresent())
            throw new RuntimeException("Board is already in the project.");

//        board.setProjectId(id);
        boards.add(board);
    }

    public void removeBoard(Board board) {
        boards.remove(board);
    }

    public ProjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public List<Board> getBoards(){
        return boards;
    };
}
