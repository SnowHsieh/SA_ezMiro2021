package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker;

import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StickerRepositoryImpl implements StickerRepository {

//    private List<Figure> figures;
    private StickerRepositoryPeer peer;

    public StickerRepositoryImpl(StickerRepositoryPeer stickerRepositoryPeer) {
//        figures = new ArrayList<>();
        this.peer = stickerRepositoryPeer;
    }

    @Override
    public List<ConnectionFigure> findAll() {
//        return figures;
        List<StickerData> stickerData = new ArrayList<>();
        peer.findAll().forEach(stickerData::add);
        return StickerMapper.transformToDomain(stickerData);
    }

    @Override
    public Optional<ConnectionFigure> findById(String id) {
//        return figures.stream().filter(x-> x.getFigureId().equals(id)).findFirst();
        return peer.findById(id).map(StickerMapper::transformToDomain);
    }

    @Override
    public void save(ConnectionFigure data) {
        peer.save(StickerMapper.transformToData(data));
    }

//    @Override
//    public void save(ConnectionFigure data) {
////        figures.removeIf(figure -> figure.getFigureId().equals(data.getFigureId()));
////        figures.add(data);
//        peer.save(FigureMapper.transformToData(data));
//    }

    @Override
    public void deleteById(String id) {
//        figures.removeIf(figure -> figure.getFigureId().equals(id));
        peer.deleteById(id);
    }

    @Override
    public List<ConnectionFigure> getStickersByBoardId(String boardId) {
//        List<Figure> result = figures.stream()
//                .filter(x -> x.getBoardId().equals(boardId) &&
//                        x.getType().equals(FigureType.Sticker))
//                .collect(Collectors.toList());
//        return result;
        return findAll().stream().filter(x -> x.getBoardId().equals(boardId) &&
                        x.getType().equals(FigureType.Sticker))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConnectionFigure> getFiguresByBoardId(String boardId) {
//        List<Figure> result = figures.stream()
//                .filter(x -> x.getBoardId().equals(boardId))
//                .collect(Collectors.toList());
//        return result;
        return findAll().stream().filter(x -> x.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }
}
