package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardChannel;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

	public static List<Board> transformToDomain(List<BoardData> datas) {
		List<Board> boards = new ArrayList<>();
		datas.forEach(x -> boards.add(transformToDomain(x)));
		return boards;
	}

	public static Board transformToDomain(BoardData data) {

		Board board = new Board(data.getTeamId(), data.getBoardId(), data.getBoardName(), new BoardChannel(data.getBoardChannel()));

		if(data.getCommittedFigureDatas() != null)
			for (CommittedFigureData committedFigureData : data.getCommittedFigureDatas()) {
				board.commitFigure(committedFigureData.getBoardId(), committedFigureData.getFigureId(), committedFigureData.getzOrder());
			}

		board.clearDomainEvents();
		return board;
	}

	public static BoardData transformToData(Board board) {
		BoardData data = new BoardData(
				board.getTeamId(),
				board.getId(),
				board.getBoardName(),
				board.getBoardChannel());

		List<CommittedFigure> committedFigures = new ArrayList<>();
		for (CommittedFigure committedFigure : board.getCommittedFigures().values()) {
			committedFigures.add(committedFigure);
		}

		data.setCommittedFigureDatas(CommittedFigureMapper.transformToData(committedFigures));

		return data;
	}
}