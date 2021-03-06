package ntut.csie.sslab.miro.usecase.figure.sticker.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;

import java.util.UUID;

public class CreateStickerUseCaseImpl implements CreateStickerUseCase {
    private StickerRepository stickerRepository;
    private DomainEventBus domainEventBus;

    public CreateStickerUseCaseImpl(StickerRepository stickerRepository, DomainEventBus domainEventBus) {
        this.stickerRepository = stickerRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateStickerInput input, CqrsCommandOutput output) {
        try{
            String stickerId = UUID.randomUUID().toString();
            ConnectionFigure sticker = new Sticker(input.getBoardId(),
                    stickerId,
                    input.getContent(),
                    input.getWidth(),
                    input.getLength(),
                    input.getColor(),
                    input.getPosition()
            );

            stickerRepository.save(sticker);
            domainEventBus.postAll(sticker);

            output.setId(sticker.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }

    }

    @Override
    public CreateStickerInput newInput() {
        return new CreateStickerInputImpl();
    }

    private class CreateStickerInputImpl implements CreateStickerInput {
        private String boardId;
        private String content;
        private int width;
        private String color;
        private Coordinate position;
        private int length;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardIdId) {
            this.boardId = boardIdId;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }

        @Override
        public void setLength(int length){
            this.length = length;
        }

        @Override
        public int getLength(){return length;}
    }
}
