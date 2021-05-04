package ntut.csie.team5.usecase.board.getcontent;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.figure.ConvertFiguresToDto;
import ntut.csie.team5.usecase.figure.FigureDto;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

import java.util.List;

public class GetBoardContentUseCaseImpl implements GetBoardContentUseCase, GetBoardContentInput {

    private String boardId;
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public GetBoardContentUseCaseImpl(BoardRepository boardRepository,
                                      FigureRepository figureRepository,
                                      DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {
        Board board  = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<FigureDto> figureDtos = ConvertFiguresToDto.transform(figureRepository.getFiguresByBoardId(input.getBoardId()));

        output.setBoardId(input.getBoardId())
                .setFigures(figureDtos);
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
