package ntut.csie.sslab.kanban.usecase.figure.sendtoback;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.List;

public class SendFigureToBackUseCaseImpl implements SendFigureToBackUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public SendFigureToBackUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(SendFigureToBackInput input, CqrsCommandOutput output) {
        try {
            List<Figure> figures = figureRepository.getFiguresByBoardId(input.getBoardId());
            Figure figure = figures.stream().filter(x -> x.getFigureId().equals(input.getFigureId())).findAny().get();
            int oldIndex = figure.getOrder();
            figure.sendToBack();
                figureRepository.save(figure);

            for (int i = 0; i < oldIndex; i++) {
                figures.get(i).setOrder(i + 1);
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
    public SendFigureToBackInput newInput() {
        return new SendFigureToBackInputImpl() ;
    }

    private class SendFigureToBackInputImpl implements SendFigureToBackInput {
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
