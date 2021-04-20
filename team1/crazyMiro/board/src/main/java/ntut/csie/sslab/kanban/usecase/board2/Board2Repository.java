package ntut.csie.sslab.kanban.usecase.board2;

import java.util.List;

import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.ddd.usecase.AbstractRepository;

public interface Board2Repository extends AbstractRepository<Board2, String> {

//	Optional<Board> getBoardById(String id);
	List<Board2> getBoardsByUserId(String userId);
	List<Board2> getBoardsByProjectId(String projectId);

}
