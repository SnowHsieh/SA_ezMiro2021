package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetBoardContentUseCase {
    private BoardRepository boardRepository;
    private StickyNoteRepository stickyNoteRepository;
    private LineRepository lineRepository;

    public GetBoardContentUseCase(BoardRepository boardRepository, StickyNoteRepository stickyNoteRepository, LineRepository lineRepository) {
        this.boardRepository = boardRepository;
        this.stickyNoteRepository = stickyNoteRepository;
        this.lineRepository = lineRepository;
    }

    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());

        if (board.isPresent()) {
            List<Widget> widgetList = new ArrayList<>();

            for (String widgetId: board.get().getWidgetIds()) {
                Optional<Widget> stickyNote = stickyNoteRepository.findById(widgetId);
                Optional<Widget> line = lineRepository.findById(widgetId);

                if(stickyNote.isPresent()) {
                    widgetList.add(stickyNote.get());
                } else if(line.isPresent()) {
                    widgetList.add(line.get());
                } else {
                    throw new RuntimeException("Widget not found, widget id = " + widgetId);
                }
            }

            output.setWidgets(widgetList);
            output.setCommittedWidgets(board.get().getCommittedWidgets());
            output.setBoardId(board.get().getId());
        } else {
            throw new RuntimeException("Board not found, board id = " + input.getBoardId());
        }
    }
}
