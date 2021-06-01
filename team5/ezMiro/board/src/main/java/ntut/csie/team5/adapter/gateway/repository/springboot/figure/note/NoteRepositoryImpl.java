package ntut.csie.team5.adapter.gateway.repository.springboot.figure.note;

import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepositoryImpl implements NoteRepository {

    private NoteRepositoryPeer peer;

    public NoteRepositoryImpl(NoteRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public List<Note> findAll() {
        List<NoteData> noteDatas = new ArrayList<>();
        peer.findAll().forEach(noteDatas::add);
        return NoteMapper.transformToDomain(noteDatas);
    }

    @Override
    public Optional<Note> findById(String id) {
        return peer.findById(id).map(NoteMapper::transformToDomain);
    }

    @Override
    public void save(Note note) {
        NoteData data = NoteMapper.transformToData(note);
        peer.save(data);
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }
}
