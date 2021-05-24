package ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote;

import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardData;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardMapper;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.StickyNote;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;

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
        List<StickyNoteData> StickyNoteDataList = new ArrayList();
        peer.findAll().forEach(x -> StickyNoteDataList.add(x));
        return StickyNoteMapper.transformToDomain(StickyNoteDataList);
    }

    @Override
    public Optional<StickyNote> findById(UUID StickyNoteId) {
        // whenever call this , it will rebuild StickyNote again.
        return peer.findById(StickyNoteId.toString()).map(StickyNoteMapper::transformToDomain);
    }

    @Override
    public void save(StickyNote stickyNote) {
        System.out.println("stickyNote:" + stickyNote.getStyle().getColor());
        System.out.println(peer);
        peer.save(StickyNoteMapper.transformToData(stickyNote));
    }

    @Override
    public void deleteById(UUID StickyNoteId) {
        peer.deleteById(StickyNoteId.toString());
    }
}
