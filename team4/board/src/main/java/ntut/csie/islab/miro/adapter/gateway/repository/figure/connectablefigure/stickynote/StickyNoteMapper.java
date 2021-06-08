package ntut.csie.islab.miro.adapter.gateway.repository.figure.connectablefigure.stickynote;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.StickyNote;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StickyNoteMapper {

    public static StickyNote transformToDomain(StickyNoteData stickyNoteData) {
        StickyNote stickyNote = new StickyNote(
                UUID.fromString(stickyNoteData.getBoardId()),
                UUID.fromString(stickyNoteData.getStickyNoteId()),
                new Position(
                        stickyNoteData.getPositionX(),
                        stickyNoteData.getPositionY()),
                stickyNoteData.getContent(),
                new Style(
                        stickyNoteData.getFontSize(),
                        ShapeKindEnum.fromInteger(stickyNoteData.getShapeKind()),
                        stickyNoteData.getWidth(),
                        stickyNoteData.getHeight(),
                        stickyNoteData.getColor())
                );

        stickyNote.clearDomainEvents();
        return stickyNote;
    }

    public static List<StickyNote> transformToDomain(List<StickyNoteData> stickyNoteDataList) {
        List<StickyNote> stickyNoteList = new ArrayList<>();
        for(StickyNoteData  stickyNoteData : stickyNoteDataList){
            StickyNote stickyNote = transformToDomain(stickyNoteData);
            stickyNoteList.add(stickyNote);
        }
        return stickyNoteList;

    }

    public static StickyNoteData transformToData(StickyNote stickyNote) {
        StickyNoteData stickyNoteData = new StickyNoteData(
                stickyNote.getBoardId().toString(),
                stickyNote.getFigureId().toString(),
                stickyNote.getPosition().getX(),
                stickyNote.getPosition().getY(),
                stickyNote.getContent(),
                stickyNote.getStyle().getFontSize(),
                stickyNote.getStyle().getShape().ordinal(),
                stickyNote.getStyle().getWidth(),
                stickyNote.getStyle().getHeight(),
                stickyNote.getStyle().getColor()
        );
        return stickyNoteData;
    }
}
