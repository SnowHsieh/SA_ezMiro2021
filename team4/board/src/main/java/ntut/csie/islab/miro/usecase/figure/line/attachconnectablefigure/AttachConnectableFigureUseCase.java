package ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class AttachConnectableFigureUseCase {
    private DomainEventBus domainEventBus;
    private LineRepository lineRepository;

    public AttachConnectableFigureUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.lineRepository = lineRepository;
    }

    public AttachConnectableFigureInput newInput() {
        return new AttachConnectableFigureInput();
    }

    public void execute(AttachConnectableFigureInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Attach line to figure failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.attachConnectableFigure(input.getConnectableFigureId(),input.getAttachEndPointKind());

        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("AttachConnectableFigure success");
    }
}
