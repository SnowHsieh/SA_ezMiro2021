package ntut.csie.islab.miro.usecase.figure.line.changecolor;

import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.StickyNote;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.StickyNoteRepository;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.changecolor.ChangeStickyNoteColorInput;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeLineColorUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public ChangeLineColorUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeLineColorInput newInput() {
        return new ChangeLineColorInput();
    }

    public void execute(ChangeLineColorInput input, CqrsCommandPresenter output) {
        Line line = lineRepository.findById(input.getFigureId()).orElse(null);
        if (null == line){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("change line color failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.changeColor(input.getColor());
        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("change line color success");

    }
}
