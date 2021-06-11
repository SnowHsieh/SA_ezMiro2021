package ntut.csie.team5.usecase.figure.line.connect;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;
import ntut.csie.team5.usecase.figure.line.LineRepository;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineInput;

public class ConnectLineUseCaseImpl implements ConnectLineUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public ConnectLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ConnectLineInput newInput() {
        return new ConnectLineInputImpl();
    }

    @Override
    public void execute(ConnectLineInput input, CqrsCommandOutput output) {
        Line line = lineRepository.findById(input.getLineId()).orElse(null);

        if (null == line) {
            output.setId(input.getLineId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Connect line failed: line not found, line id = " + input.getLineId());
            return;
        }

        line.connectToFigure(input.getEndpointId(), input.getFigureId());
        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ConnectLineInputImpl implements ConnectLineInput {

        private String lineId;
        private String endpointId;
        private String figureId;

        @Override
        public String getLineId() {
            return lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public String getEndpointId() {
            return endpointId;
        }

        @Override
        public void setEndpointId(String endpointId) {
            this.endpointId = endpointId;
        }

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }
    }
}
