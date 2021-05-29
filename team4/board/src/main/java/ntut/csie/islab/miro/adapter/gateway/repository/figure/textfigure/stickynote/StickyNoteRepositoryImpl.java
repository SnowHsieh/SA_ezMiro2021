package ntut.csie.islab.miro.adapter.gateway.repository.figure.textfigure.stickynote;

import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.StickyNote;
import ntut.csie.islab.miro.usecase.figure.textfigure.StickyNoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StickyNoteRepositoryImpl implements StickyNoteRepository {
    private StickyNoteRepositoryPeer peer ;

    public StickyNoteRepositoryImpl(StickyNoteRepositoryPeer peer) {
        this.peer = peer;
    }


    @Override
    public List<StickyNote> findAll() {
        List<StickyNoteData> stickyNoteDataList = new ArrayList();
        peer.findAll().forEach(x -> stickyNoteDataList.add(x));
        return StickyNoteMapper.transformToDomain(stickyNoteDataList);
    }

    @Override
    public Optional<StickyNote> findById(UUID stickyNoteId) {
        // whenever call this , it will rebuild StickyNote again.
        return peer.findById(stickyNoteId.toString()).map(StickyNoteMapper::transformToDomain);
    }

    @Override
    public void save(StickyNote stickyNote) {
        peer.save(StickyNoteMapper.transformToData(stickyNote));
    }

    @Override
    public void deleteById(UUID stickyNoteId) {
        peer.deleteById(stickyNoteId.toString());
    }
}
