package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FigureRepositoryImpl implements FigureRepository {

//    private List<Figure> figures;
    private FigureRepositoryPeer peer;

    public FigureRepositoryImpl(FigureRepositoryPeer figureRepositoryPeer) {
//        figures = new ArrayList<>();
        this.peer = figureRepositoryPeer;
    }

    @Override
    public List<Figure> findAll() {
//        return figures;
        List<FigureData> figureDatas = new ArrayList<>();
        peer.findAll().forEach(figureDatas::add);
        return FigureMapper.transformToDomain(figureDatas);
    }

    @Override
    public Optional<Figure> findById(String id) {
//        return figures.stream().filter(x-> x.getFigureId().equals(id)).findFirst();
        return peer.findById(id).map(FigureMapper::transformToDomain);
    }

    @Override
    public void save(Figure data) {
//        figures.removeIf(figure -> figure.getFigureId().equals(data.getFigureId()));
//        figures.add(data);
        peer.save(FigureMapper.transformToData(data));
    }

    @Override
    public void deleteById(String id) {
//        figures.removeIf(figure -> figure.getFigureId().equals(id));
        peer.deleteById(id);
    }

    @Override
    public List<Figure> getStickersByBoardId(String boardId) {
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
    public List<Figure> getFiguresByBoardId(String boardId) {
//        List<Figure> result = figures.stream()
//                .filter(x -> x.getBoardId().equals(boardId))
//                .collect(Collectors.toList());
//        return result;
        return findAll().stream().filter(x -> x.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }
}
