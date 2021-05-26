package ntut.csie.team5.usecase.board.getcontent;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.CommittedFigure;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.figure.ConvertFiguresToDto;
import ntut.csie.team5.usecase.figure.FigureDto;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class GetBoardContentUseCaseImpl implements GetBoardContentUseCase, GetBoardContentInput {

    private String boardId;
    private BoardRepository boardRepository;
    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public GetBoardContentUseCaseImpl(BoardRepository boardRepository,
                                      NoteRepository noteRepository,
                                      DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board content failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<FigureDto> figureDtos = new ArrayList<>();
        for (CommittedFigure committedFigure : board.getCommittedFigures()) {
            Figure figure = noteRepository.findById(committedFigure.figureId()).orElse(null);
            if (null == figure) {
                output.setBoardId(input.getBoardId())
                        .setExitCode(ExitCode.FAILURE)
                        .setMessage("Get board content failed: figure not found, figure id = " + committedFigure.figureId());
                domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
                return;
            }
            FigureDto figureDto = ConvertFiguresToDto.transform(figure);
            figureDtos.add(figureDto);
        }

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
