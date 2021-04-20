package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkflowRepositoryPeer extends CrudRepository<WorkflowData, String> {

    @Query(value = "select * from workflow where board_id = :boardId",
            nativeQuery = true)
    List<WorkflowData> getWorkflowsByBoardId(@Param("boardId") String boardId);
}