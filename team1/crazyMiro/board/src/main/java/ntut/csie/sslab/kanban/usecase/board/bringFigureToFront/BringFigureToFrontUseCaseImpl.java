package ntut.csie.sslab.kanban.usecase.board.bringFigureToFront;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

import java.util.List;

public class BringFigureToFrontUseCaseImpl implements BringFigureToFrontUseCase {
    BoardRepository boardRepository;
    DomainEventBus domainEventBus;

    public BringFigureToFrontUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(BringFigureToFrontInput input, CqrsCommandOutput output) {
        try{
            Board board = boardRepository.findById(input.getBoardId()).get();
            List<CommittedFigure> figures = board.getCommittedFigures();
            if (figures.get(figures.size() - 1).getFigureId().equals(input.getFigureId())) {
                return;
            }

            board.bringFigureToFront(input.getFigureId());

            boardRepository.save(board);
            domainEventBus.postAll(board);
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.SUCCESS);
        }
        catch (Exception e){
            output.setMessage(e.getMessage())
                    .setExitCode(ExitCode.FAILURE);
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
