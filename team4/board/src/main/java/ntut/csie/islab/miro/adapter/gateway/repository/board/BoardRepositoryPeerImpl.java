package ntut.csie.islab.miro.adapter.gateway.repository.board;

import java.util.Optional;
import java.util.UUID;

public class BoardRepositoryPeerImpl implements BoardRepositoryPeer {
    @Override
    public <S extends BoardData> S save(S s) {
        return null;
    }

    @Override
    public <S extends BoardData> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<BoardData> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public Iterable<BoardData> findAll() {
        return null;
    }

    @Override
    public Iterable<BoardData> findAllById(Iterable<UUID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(BoardData boardData) {

    }

    @Override
    public void deleteAll(Iterable<? extends BoardData> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
