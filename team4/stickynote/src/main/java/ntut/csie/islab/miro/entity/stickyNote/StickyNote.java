package ntut.csie.islab.miro.entity.stickyNote;

import ntut.csie.islab.miro.entity.figure.Style;
import ntut.csie.islab.miro.entity.figure.Position;
import ntut.csie.islab.miro.entity.Figure;
import java.util.UUID;

public class StickyNote extends Figure {
    public StickyNote(UUID boardId, Position position, String content, Style style) {
        super(boardId,position,content,style);
    }
}
