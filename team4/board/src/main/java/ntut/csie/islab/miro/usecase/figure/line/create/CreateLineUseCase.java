package ntut.csie.islab.miro.usecase.figure.line.create;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class CreateLineUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public CreateLineUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public CreateLineInput newInput() {
        return new CreateLineInput();
    }

    public void execute(CreateLineInput input, CqrsCommandPresenter output) {
        Figure line = new Line(input.getBoardId(),input.getPositionList(),input.getStrokeWidth(),input.getColor());
        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Create line success");

    }
}
