package ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.List;

public class BringStickerToFrontUseCaseImpl implements BringStickerToFrontUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public BringStickerToFrontUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(BringStickerToFrontInput input, CqrsCommandOutput output) {
        try {
            List<Figure> stickers = figureRepository.getStickersByBoardId(input.getBoardId());
            Figure sticker = stickers.stream().filter(x -> x.getFigureId().equals(input.getFigureId())).findAny().get();
            int oldIndex = sticker.getOrder();
            sticker.bringToFront(stickers.size()-1);
            figureRepository.save(sticker);

            for (int i = oldIndex + 1; i < stickers.size(); i++) {
                stickers.get(i).setOrder(i - 1);
                figureRepository.save(stickers.get(i));
            }
            domainEventBus.postAll(sticker);

            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }

    }

    @Override
    public BringStickerToFrontInput newInput() {
        return new BringStickerToFrontInputImpl();
    }


    private class BringStickerToFrontInputImpl implements BringStickerToFrontInput {
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
