package ntut.csie.islab.miro.usecase.board.getboardcontent;

import ntut.csie.islab.miro.adapter.presenter.getcontent.GetBoardContentPresenter;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.CommittedFigure;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.islab.miro.usecase.figure.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.usecase.figure.FigureDto;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.ArrayList;
import java.util.List;

public class GetBoardContentUseCase {

    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;
    private StickyNoteRepository stickyNoteRepository;
    private LineRepository lineRepository;

    public GetBoardContentUseCase(DomainEventBus domainEventBus, BoardRepository boardRepository, StickyNoteRepository stickyNoteRepository, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.boardRepository = boardRepository;
        this.stickyNoteRepository = stickyNoteRepository;
        this.lineRepository = lineRepository;
    }

    public GetBoardContentInput newInput() {
        return new GetBoardContentInput();
    }


    public void execute(GetBoardContentInput input, GetBoardContentPresenter presenter) {
        Board board = this.boardRepository.findById(input.getBoardId()).orElse(null);
        System.out.println("board object:" + board);
        if (null == board) {
            presenter.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<CommittedFigure> CommittedFigureList = board.getCommittedFigures();
        List<Figure> figureList = new ArrayList<>();


        for (CommittedFigure f : CommittedFigureList) {
            if (f.getFigureType() == FigureTypeEnum.STICKYNOTE) {
                figureList.add(this.stickyNoteRepository.findById(f.getFigureId()).get());
            } else if (f.getFigureType() == FigureTypeEnum.LINE) {
                figureList.add(this.lineRepository.findById(f.getFigureId()).get());
            }
        }

        List<FigureDto> figureDtos = ConvertFigureToDto.transform(figureList);


        presenter.setBoardId(board.getBoardId()).setFigures(figureDtos);
    }
}
