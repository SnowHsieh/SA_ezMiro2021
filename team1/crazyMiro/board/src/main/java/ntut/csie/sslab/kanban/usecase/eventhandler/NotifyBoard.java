package ntut.csie.sslab.kanban.usecase.eventhandler;


import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

import java.util.Optional;

public class NotifyBoard {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardRepository boardRepository,
                       DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void handleStickerCreated(StickerCreated stickerCreated) {
        Optional<Board> board = boardRepository.findById(stickerCreated.getBoardId());
        if (!board.isPresent())
            throw new RuntimeException("Board not found, board id = " + stickerCreated.getBoardId());

        board.get().commitFigure(stickerCreated.getFigureId());
        boardRepository.save(board.get());
        domainEventBus.postAll(board.get());
    }

    public void handleStickerDeleted(StickerDeleted stickerDeleted) {
        Optional<Board> board = boardRepository.findById(stickerDeleted.getBoardId());
        if (!board.isPresent())
            throw new RuntimeException("Board not found, board id = " + stickerDeleted.getBoardId());

        board.get().uncommitFigure(stickerDeleted.getFigureId());
        boardRepository.save(board.get());
        domainEventBus.postAll(board.get());

    }

//    public void whenWorkspaceCreated(WorkspaceCreated workspaceCreated) {
//        Optional<Board> board = boardRepository.findById(workspaceCreated.boardId());
//        if (!board.isPresent())
//            throw new RuntimeException("Board not found, board id = " + workspaceCreated.boardId());
//
//        board.get().commitWorkflow(workflowCreated.workflowId());
//        boardRepository.save(board.get());
//
//        domainEventBus.postAll(board.get());
//    }
//
//    public void whenWorkflowDeleted(WorkflowDeleted workflowDeleted) {
//        Optional<Board> board = boardRepository.findById(workflowDeleted.boardId());
//        if (!board.isPresent())
//            throw new RuntimeException("Board not found, board id = " + workflowDeleted.boardId());
//
//        board.get().uncommitworkflow(workflowDeleted.workflowId());
//        boardRepository.save(board.get());
//
//        domainEventBus.postAll(board.get());
//    }


    

}
