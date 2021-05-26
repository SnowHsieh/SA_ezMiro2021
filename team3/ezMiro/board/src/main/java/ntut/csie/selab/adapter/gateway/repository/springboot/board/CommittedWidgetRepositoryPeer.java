package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetDataKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommittedWidgetRepositoryPeer extends CrudRepository<CommittedWidgetData, CommittedWidgetDataKey> {
    long countByBoard(BoardData board);
}