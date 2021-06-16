package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommittedWidgetRepositoryPeer extends CrudRepository<CommittedWidgetData, CommittedWidgetDataKey> {
    long countByBoard(BoardData board);
}