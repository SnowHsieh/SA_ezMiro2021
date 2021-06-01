package ntut.csie.islab.miro.usecase.figure.line.changepath;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeLinePathUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public ChangeLinePathUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeLinePathInput newInput() {
        return new ChangeLinePathInput();
    }

    public void execute(ChangeLinePathInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete line failed: line not found, line id = " + input.getFigureId());
            return;
        }

        line.changeLinePath(input.getPositionList());
        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("ChangeLinePath success");

    }
}
