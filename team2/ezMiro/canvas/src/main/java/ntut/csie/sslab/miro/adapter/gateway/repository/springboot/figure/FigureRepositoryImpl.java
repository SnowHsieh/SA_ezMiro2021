package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FigureRepositoryImpl implements FigureRepository {
    private NoteRepositoryPeer notePeer;

    public FigureRepositoryImpl(NoteRepositoryPeer notePeer) {
        this.notePeer = notePeer;
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
    public Optional<Figure> findById(String figureId) {
        //TODO: Find by id from different peers.
        return notePeer.findById(figureId).map(NoteMapper::transformToDomain);
    }

    @Override
    public void save(Figure data) {
        if (data instanceof Note) {
            NoteData noteData = NoteMapper.transformToData((Note) data);
            notePeer.save(noteData);
        }
    }

    @Override
    public void deleteById(String figureId) {
        notePeer.deleteById(figureId);
    }

    @Override
    public void deleteAll() {
        notePeer.deleteAll();
    }

    @Override
    public List<Figure> findByBoardId(String boardId) {
        return notePeer.findByBoardId(boardId).stream().map(NoteMapper::transformToDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Note> findNoteById(String noteId) {
        return notePeer.findById(noteId).map(NoteMapper::transformToDomain);
    }
}