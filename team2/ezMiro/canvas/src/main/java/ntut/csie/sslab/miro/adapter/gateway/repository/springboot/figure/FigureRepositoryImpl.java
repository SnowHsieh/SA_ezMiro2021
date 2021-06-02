package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FigureRepositoryImpl implements FigureRepository {
    private NoteRepositoryPeer notePeer;
    private LineRepositoryPeer linePeer;

    public FigureRepositoryImpl(NoteRepositoryPeer notePeer, LineRepositoryPeer linePeer) {
        this.notePeer = notePeer;
        this.linePeer = linePeer;
    }

    @Override
    public List<Figure> findAll() {
        List<Figure> figures = new ArrayList<>();
        figures.addAll(findAllNotes());
        return figures;
    }

    @Override
    public List<Note> findAllNotes() {
        List<NoteData> noteDatas = new ArrayList();
        notePeer.findAll().forEach(noteDatas::add);
        return NoteMapper.transformToDomain(noteDatas);
    }

    @Override
    public List<Line> findAllLines() {
        List<LineData> lineDatas = new ArrayList();
        linePeer.findAll().forEach(lineDatas::add);
        return LineMapper.transformToDomain(lineDatas);
    }

    @Override
    public Optional<Figure> findById(String figureId) {
        //TODO: Find by id from different peers.
        return notePeer.findById(figureId).map(NoteMapper::transformToDomain);
    }

    @Override
    public void save(Figure data) {
        if (data instanceof Note) {
            NoteData noteData = NoteMapper.transformToData((Note) data);
            notePeer.save(noteData);
        } else if(data instanceof Line) {
            LineData lineData = LineMapper.transformToData((Line) data);
            linePeer.save(lineData);
        }
    }

    @Override
    public void deleteById(String figureId) {
        if (notePeer.existsById(figureId)) {
            notePeer.deleteById(figureId);
        } else if (linePeer.existsById(figureId)) {
            linePeer.deleteById(figureId);
        }
    }

    @Override
    public void deleteAll() {
        notePeer.deleteAll();
    }

    @Override
    public List<Figure> findByBoardId(String boardId) {
        List<Figure> result = new ArrayList<>();
        result.addAll(notePeer.findByBoardId(boardId).stream().map(NoteMapper::transformToDomain).collect(Collectors.toList()));
        result.addAll(linePeer.findByBoardId(boardId).stream().map(LineMapper::transformToDomain).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Optional<Note> findNoteById(String noteId) {
        return notePeer.findById(noteId).map(NoteMapper::transformToDomain);
    }

    @Override
    public Optional<Line> findLineById(String lineId) {
        return linePeer.findById(lineId).map(LineMapper::transformToDomain);
    }
}