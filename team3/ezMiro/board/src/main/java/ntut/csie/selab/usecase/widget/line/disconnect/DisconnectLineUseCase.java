package ntut.csie.selab.usecase.widget.line.disconnect;

import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.Optional;

public class DisconnectLineUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public DisconnectLineUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(DisconnectLineInput input, DisconnectLineOutput output) {
        Optional<Widget> line = lineRepository.findById(input.getLineId());

        if (line.isPresent()) {
            Line selectedLine = (Line) line.get();
            selectedLine.clearDomainEvents();
            selectedLine.disconnectWidgetByEndPoint(input.getEndPoint());
            lineRepository.save(selectedLine);
            output.setLineId(selectedLine.getId());
            domainEventBus.postAll(selectedLine);
        } else {
            throw new RuntimeException("line not found, line id = " + input.getLineId());
        }
    }
}
