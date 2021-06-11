package ntut.csie.team5.usecase.figure.line.draw;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.entity.model.figure.line.LineBuilder;
import ntut.csie.team5.usecase.figure.line.LineRepository;

public class DrawLineUseCaseImpl implements DrawLineUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public DrawLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DrawLineInput newInput() {
        return new DrawLineInputImpl();
    }

    @Override
    public void execute(DrawLineInput input, CqrsCommandOutput output) {

        Line line = LineBuilder.newInstance()
                .boardId(input.getBoardId())
                .endpointA(input.getEndpointA())
                .endpointB(input.getEndpointB())
                .figureType(input.getFigureType())
                .build();
        System.out.println("DrawLineUseCaseImpl");
        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }


    private class DrawLineInputImpl implements DrawLineInput {

        private String boardId;
        private Endpoint endpointA;
        private Endpoint endpointB;
        private FigureType figureType;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public Endpoint getEndpointA() {
            return endpointA;
        }

        @Override
        public void setEndpointA(Endpoint endpointA) {
            this.endpointA = endpointA;
        }

        @Override
        public Endpoint getEndpointB() {
            return endpointB;
        }

        @Override
        public void setEndpointB(Endpoint endpointB) {
            this.endpointB = endpointB;
        }

        @Override
        public FigureType getFigureType() {
            return figureType;
        }

        @Override
        public void setFigureType(FigureType figureType) {
            this.figureType = figureType;
        }
    }
}
