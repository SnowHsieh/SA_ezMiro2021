package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;


import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LineMapper {

    public static Line transformToDomain(LineData lineData) {
        List<Position> positionList = new ArrayList<>();
        for (PositionData positionData : lineData.getPositionDataList()) {
            positionList.add(
                    new Position(
                        positionData.getPositionX(),
                        positionData.getPositionY()
                    )
            );
        }

        Line line = new Line(
                UUID.fromString(lineData.getBoardId()),
                UUID.fromString(lineData.getLineId()),
                positionList,
                lineData.getStrokeWidth(),
                lineData.getColor()
        );

        if(!lineData.getSrcTextFigureId().equals("")) {
            line.setSrcTextFigureId(UUID.fromString(lineData.getSrcTextFigureId()));
        }
        if(!lineData.getDestTextFigureId().equals("")) {
            line.setDestTextFigureId(UUID.fromString(lineData.getDestTextFigureId()));
        }

        line.clearDomainEvents();
        return line;
    }

    public static List<Line> transformToDomain(List<LineData> lineDataList) {
        List<Line> lineList = new ArrayList<>();
        for (LineData lineData : lineDataList) {
            Line line = transformToDomain(lineData);
            lineList.add(line);
        }
        return lineList;
    }

    public static LineData transformToData(Line line) {

        List< PositionData > positionDataList = new ArrayList<>();
        List<Position> positionList = line.getPositionList();
        for (int i =0 ; i < positionList.size() ; i++) {
            PositionData item = new PositionData(
                    line.getId().toString(),
                    i,
                    positionList.get(i).getX(),
                    positionList.get(i).getY());
            positionDataList.add(item);
        }

        LineData lineData = new LineData(
                line.getBoardId().toString(),
                line.getId().toString(),
                positionDataList,
                line.getStrokeWidth(),
                line.getColor(),
                line.getSrcArrowKind().ordinal(),
                line.getDestArrowKind().ordinal()
        );

        if(line.getSrcTextFigureId()!=null) {
            lineData.setSrcTextFigureId(line.getSrcTextFigureId().toString());
        }
        if(line.getDestTextFigureId()!=null) {
            lineData.setDestTextFigureId(line.getDestTextFigureId().toString());
        }


        return lineData;
    }
}
