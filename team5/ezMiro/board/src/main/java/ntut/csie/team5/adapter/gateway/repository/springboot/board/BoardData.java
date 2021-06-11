package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="board")
public class BoardData {

    @Id
    @Column(name="board_id")
    private String boardId;

    @Column(name="project_id", nullable = false)
    private String projectId;

    @Column(name="board_name")
    private String name;

    @OrderBy("z_order ASC")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<CommittedFigureData> committedFigureDatas;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<BoardSessionData> boardSessionDatas;

    public BoardData() {
        committedFigureDatas = new HashSet<>();
        boardSessionDatas = new HashSet<>();
    }

    public BoardData(String projectId, String boardId, String boardName) {
        this();
        this.projectId = projectId;
        this.boardId = boardId;
        this.name = boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommittedFigureData> getCommittedFigureDatas() {
        return new ArrayList<>(committedFigureDatas);
    }

    public void setCommittedFigureDatas(List<CommittedFigureData> committedFigureDatas) {
        this.committedFigureDatas = new HashSet<>(committedFigureDatas);
    }

    public List<BoardSessionData> getBoardSessionDatas() {
        return new ArrayList<>(boardSessionDatas);
    }

    public void setBoardSessionDatas(List<BoardSessionData> boardSessionDatas) {
        this.boardSessionDatas = new HashSet<>(boardSessionDatas);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.boardId);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.projectId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardData other = (BoardData) obj;
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.boardId, other.boardId);
    }
}
