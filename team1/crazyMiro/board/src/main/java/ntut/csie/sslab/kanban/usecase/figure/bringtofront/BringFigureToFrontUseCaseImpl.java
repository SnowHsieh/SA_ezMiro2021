package ntut.csie.sslab.kanban.usecase.figure.bringtofront;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.List;

public class BringFigureToFrontUseCaseImpl implements BringFigureToFrontUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public BringFigureToFrontUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(BringFigureToFrontInput input, CqrsCommandOutput output) {
        try {
            List<Figure> figures = figureRepository.getFiguresByBoardId(input.getBoardId());
            Figure figure = figures.stream().filter(x -> x.getFigureId().equals(input.getFigureId())).findAny().get();
            int oldIndex = figure.getOrder();
            figure.bringToFront(figures.size()-1);
            figureRepository.save(figure);

            for (int i = oldIndex + 1; i < figures.size(); i++) {
                figures.get(i).setOrder(i - 1);
                figureRepository.save(figures.get(i));
            }
            domainEventBus.postAll(figure);

            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }

    }

    @Override
    public BringFigureToFrontInput newInput() {
        return new BringFigureToFrontInputImpl();
    }


    private class BringFigureToFrontInputImpl implements BringFigureToFrontInput {
        private String boardId;
        private String figureId;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
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
