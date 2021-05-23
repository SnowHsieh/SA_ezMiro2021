package ntut.csie.sslab.miro.usecase.figure.sticker.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;

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
            sticker.deleteSticker();
            figureRepository.deleteById(sticker.getFigureId());
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
        private String boardId;

        @Override
        public void setFigureId(String stickerId) {
            this.stickerId = stickerId;
        }

        @Override
        public String getFigureId() {
            return stickerId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
