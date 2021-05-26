package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import ntut.csie.team5.entity.model.board.CommittedFigure;

import java.util.ArrayList;
import java.util.List;

public class CommittedFigureMapper {

    public static CommittedFigureData transformToData(CommittedFigure committedFigures) {
        CommittedFigureData committedFigureData = new CommittedFigureData(
            committedFigures.boardId(),
            committedFigures.figureId(),
            committedFigures.zOrder()
        );
        return committedFigureData;
    }

    public static List<CommittedFigureData> transformToData(List<CommittedFigure> committedFigures) {
        List<CommittedFigureData> committedFigureDatas = new ArrayList<>();
        committedFigures.forEach(committedFigure -> {
            committedFigureDatas.add(transformToData(committedFigure));
        });
        return committedFigureDatas;
    }
}
