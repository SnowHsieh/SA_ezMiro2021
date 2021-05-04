package ntut.csie.sslab.kanban.usecase.figure.sticker.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.entity.model.figure.Sticker;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.List;

public class DeleteStickerUseCaseImpl implements DeleteStickerUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public DeleteStickerUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(DeleteStickerInput input, CqrsCommandOutput output) {
        try {
            Sticker sticker = (Sticker)figureRepository.findById(input.getFigureId()).get();
            String boardId = sticker.getBoardId();
            int order = sticker.getOrder();
            sticker.deleteSticker();

            figureRepository.deleteById(sticker.getFigureId());
            List<Figure> stickers = figureRepository.getStickersByBoardId(boardId);
            for(int i = order; i < stickers.size(); i++){
                stickers.get(i).setOrder(i);
                figureRepository.save(stickers.get(i));
            }

            domainEventBus.postAll(sticker);

            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
        } catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public DeleteStickerInput newInput() {
        return new DeleteStickerInputImpl();
    }


    private class DeleteStickerInputImpl implements DeleteStickerInput {
        private String stickerId;
        @Override
        public void setFigureId(String stickerId) {
            this.stickerId = stickerId;
        }

        @Override
        public String getFigureId() {
            return stickerId;
        }


    }
}
