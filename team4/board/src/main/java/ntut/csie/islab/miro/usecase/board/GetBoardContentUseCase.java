package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.islab.miro.figure.usecase.figure.FigureDto;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.List;

public class GetBoardContentUseCase {

    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;

    public GetBoardContentUseCase(DomainEventBus domainEventBus, BoardRepository boardRepository ,FigureRepository figureRepository) {
        this.domainEventBus = domainEventBus;
        this.boardRepository = boardRepository;
        this.figureRepository = figureRepository;
    }

    public GetBoardContentInput newInput() {
        return new GetBoardContentInput();
    }


    public void execute(GetBoardContentInput input, GetBoardContentPresenter presenter) {
        Board board = this.boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board){
            presenter.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }


        List<Figure> figureList  = this.figureRepository.findFiguresByBoardId(board.getBoardId());
        List<FigureDto> figureDtos = ConvertFigureToDto.transform(figureList);


        presenter.setBoardId(board.getBoardId()).setFigures(figureDtos);
    }
}
