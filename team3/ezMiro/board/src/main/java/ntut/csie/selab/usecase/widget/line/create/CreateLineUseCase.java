package ntut.csie.selab.usecase.widget.line.create;

import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.UUID;

public class CreateLineUseCase {
    LineRepository lineRepository;
    DomainEventBus domainEventBus;

    public CreateLineUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateLineInput input, CreateLineOutput output) {
        String lineId = UUID.randomUUID().toString();
        Line line = new Line(lineId, input.getBoardId(), input.getCoordinate());

        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setLineId(line.getId());
        output.setBoardId(line.getBoardId());
        output.setCoordinate(line.getCoordinate());
    }
}
