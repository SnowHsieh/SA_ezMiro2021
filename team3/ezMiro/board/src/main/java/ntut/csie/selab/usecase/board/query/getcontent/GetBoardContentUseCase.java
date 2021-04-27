package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetBoardContentUseCase {
    private BoardRepository boardRepository;
    private WidgetRepository widgetRepository;

    public GetBoardContentUseCase(BoardRepository boardRepository, WidgetRepository widgetRepository) {
        this.boardRepository = boardRepository;
        this.widgetRepository = widgetRepository;
    }

    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());

        if (board.isPresent()) {
            List<Widget> widgetList = new ArrayList<>();

            for (String widgetId: board.get().getWidgetIds()) {
                Optional<Widget> widget = widgetRepository.findById(widgetId);
                if(widget.isPresent()) {
                    widgetList.add(widget.get());
                } else {
                    throw new RuntimeException("Widget not found, widget id = " + widgetId);
                }
            }

            output.setWidgets(widgetList);
            output.setBoardId(board.get().getBoardId());
        } else {
            throw new RuntimeException("Board not found, board id = " + input.getBoardId());
        }
    }
}
