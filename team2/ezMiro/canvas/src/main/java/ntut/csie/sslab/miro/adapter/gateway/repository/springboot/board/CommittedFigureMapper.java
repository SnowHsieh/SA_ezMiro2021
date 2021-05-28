package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import java.util.ArrayList;
import java.util.List;

public class CommittedFigureMapper {

    public static CommittedFigureData transformToData(CommittedFigure committedFigure){
        CommittedFigureData data = new CommittedFigureData(
                committedFigure.getBoardId(),
                committedFigure.getFigureId(),
                committedFigure.getZOrder()
        );
        return data;
    }

    public static List<CommittedFigureData> transformToData(List<CommittedFigure> committedFigures){
        List<CommittedFigureData> datas = new ArrayList<>();
        committedFigures.forEach( x -> datas.add(transformToData(x)));
        return datas;
    }
}