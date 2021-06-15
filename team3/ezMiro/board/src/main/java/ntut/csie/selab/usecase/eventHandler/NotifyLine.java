package ntut.csie.selab.usecase.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.entity.model.widget.event.WidgetDeleted;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.List;

public class NotifyLine {
    private BoardRepository boardRepository;
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public NotifyLine(BoardRepository boardRepository, LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenWidgetDeleted(WidgetDeleted widgetDeleted) {
        List<Widget> lines = lineRepository.findAllByHeadWidgetOrTailWidget(widgetDeleted.getWidgetId());

        for(Widget line : lines) {
            ((Line) line).disconnectWidgetById(widgetDeleted.getWidgetId());
        }
        lineRepository.saveAll(lines);
    }
}
