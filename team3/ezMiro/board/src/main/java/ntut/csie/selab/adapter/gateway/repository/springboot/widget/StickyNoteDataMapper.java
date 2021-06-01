package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class StickyNoteDataMapper {

    public static StickyNoteData domainToData(StickyNote stickyNote) {
        return new StickyNoteData(
                stickyNote.getId(),
                stickyNote.getBoardId(),
                stickyNote.getCoordinate().getTopLeft().x,
                stickyNote.getCoordinate().getTopLeft().y,
                stickyNote.getCoordinate().getBottomRight().x,
                stickyNote.getCoordinate().getBottomRight().y,
                stickyNote.getColor(),
                stickyNote.getTextColor(),
                stickyNote.getText(),
                stickyNote.getFontSize()
        );
    }

    public static List<StickyNoteData> domainToData(List<Widget> widgets) {
        List<StickyNoteData> stickyNoteData = new ArrayList<>();
        widgets.forEach(widget -> stickyNoteData.add(domainToData((StickyNote) widget)));
        return stickyNoteData;
    }

    public static Widget dataToDomain(StickyNoteData stickyNoteData) {
        Coordinate coordinate = new Coordinate(stickyNoteData.getTopLeftX(), stickyNoteData.getTopLeftY(), stickyNoteData.getBottomRightX(), stickyNoteData.getBottomRightY());
        // TODO 要把widget型態(sticky note, line, etc.)存入資料庫，用Mapper時用Factory生不同的instance，這邊先stickynote寫死
        StickyNote stickyNote = new StickyNote(stickyNoteData.getWidgetId(), stickyNoteData.getBoardId(), coordinate);
        stickyNote.setColor(stickyNoteData.getColor());
        stickyNote.setTextColor(stickyNoteData.getTextColor());
        stickyNote.setText(stickyNoteData.getText());
        stickyNote.setFontSize(stickyNoteData.getFontSize());
        return stickyNote;
    }
}
