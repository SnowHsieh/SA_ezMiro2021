package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;

import java.util.List;
import java.util.Optional;

public class NotifyLine {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public NotifyLine(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public void handleStickerDeleted(StickerDeleted stickerDeleted) {
        List<Line> lines = lineRepository.findByFigureId(stickerDeleted.getFigureId());

        for (Line line:lines) {
            line.deleteLine();
            lineRepository.deleteById(line.getLineId());
            domainEventBus.postAll(line);
        }

    }
}
