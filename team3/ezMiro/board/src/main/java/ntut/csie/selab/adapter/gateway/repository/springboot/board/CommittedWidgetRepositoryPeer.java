package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommittedWidgetRepositoryPeer extends CrudRepository<CommittedWidgetData, ImmutablePair<String, String>> {
//    long countByBoardData(BoardData boardData);
}
