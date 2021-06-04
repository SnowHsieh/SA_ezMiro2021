package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class LineDataMapper {

    public static LineData domainToData(Widget widget) {
        Line line = (Line) widget;

        LineData lineData = new LineData(line.getId(), line.getBoardId(),
                line.getCoordinate().getTopLeft().x, line.getCoordinate().getTopLeft().y,
                line.getCoordinate().getBottomRight().x, line.getCoordinate().getBottomRight().y);
        if (line.getHeadWidget() != null) {
            lineData.setHeadWidget(StickyNoteDataMapper.domainToData((StickyNote) line.getHeadWidget()));
        }
        if (line.getTailWidget() != null) {
            lineData.setTailWidget(StickyNoteDataMapper.domainToData((StickyNote) line.getTailWidget()));
        }

        return lineData;
    }

    public static List<LineData> domainToData(List<Widget> lines) {
        List<LineData> lineDatas = new ArrayList<>();
        lines.forEach(line -> lineDatas.add(domainToData(line)));
        return lineDatas;
    }

    public static Line dataToDomain(LineData lineData) {
        Coordinate coordinate = new Coordinate(lineData.getTopLeftX(), lineData.getTopLeftY(), lineData.getBottomRightX(), lineData.getBottomRightY());

        Line line = new Line(lineData.getLineId(), lineData.getBoardId(), coordinate);
        if (lineData.getHeadWidget() != null) {
            line.setHeadWidget(StickyNoteDataMapper.dataToDomain(lineData.getHeadWidget()));
        }
        if (lineData.getTailWidget() != null) {
            line.setTailWidget(StickyNoteDataMapper.dataToDomain(lineData.getTailWidget()));
        }

        return line;
    }
}