package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class StickyNoteDtoMapper {

    public StickyNoteDto domainToDto(Widget widget) {
        Position position = widget.getPosition();
        int width = position.getBottomRight().x - position.getTopLeft().x;
        int height = position.getBottomRight().y - position.getTopLeft().y;
        StickyNote stickyNote = (StickyNote) widget;
        return new StickyNoteDto(
                stickyNote.getId(),
                position.getTopLeft().x,
                position.getTopLeft().y,
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
