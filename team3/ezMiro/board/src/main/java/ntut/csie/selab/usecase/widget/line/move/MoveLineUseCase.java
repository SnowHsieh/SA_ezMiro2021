package ntut.csie.selab.usecase.widget.line.move;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.Optional;

public class MoveLineUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public MoveLineUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }


    public void execute(MoveLineInput input, MoveLineOutput output) {
        Optional<Widget> line = lineRepository.findById(input.getLineId());

        if (line.isPresent()) {
            Widget selectedLine = line.get();
            selectedLine.clearDomainEvents();
            selectedLine.move(input.getCoordinate());
            lineRepository.save(selectedLine);
            domainEventBus.postAll(selectedLine);
            output.setLineId(input.getLineId());
            output.setCoordinate(input.getCoordinate());

        } else {
            throw new RuntimeException("line not found, line id = " + input.getLineId());
        }
    }
}
