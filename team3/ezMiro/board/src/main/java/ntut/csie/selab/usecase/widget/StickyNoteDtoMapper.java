package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class StickyNoteDtoMapper {

    public StickyNoteDto domainToDto(Widget widget) {
        Coordinate coordinate = widget.getCoordinate();
        int width = coordinate.getBottomRight().x - coordinate.getTopLeft().x;
        int height = coordinate.getBottomRight().y - coordinate.getTopLeft().y;
        StickyNote stickyNote = (StickyNote) widget;
        return new StickyNoteDto(
                stickyNote.getId(),
                coordinate.getTopLeft().x,
                coordinate.getTopLeft().y,
                width,
                height,
                stickyNote.getColor(),
                stickyNote.getTextColor(),
                stickyNote.getText(),
                stickyNote.getFontSize()
        );
    }

    public List<StickyNoteDto> domainToDto(List<Widget> widgets) {
        List<StickyNoteDto> stickyNoteDtos = new ArrayList<>();

        widgets.forEach(widget -> stickyNoteDtos.add(domainToDto((StickyNote) widget)));

        return stickyNoteDtos;
    }

}
