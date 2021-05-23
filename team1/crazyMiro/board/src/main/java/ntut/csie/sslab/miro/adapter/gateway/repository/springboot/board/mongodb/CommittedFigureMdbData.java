package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Document(collection = "committed_figure")
public class CommittedFigureMdbData {

    @Field("figure_id")
    private String figureId;

    @Field("z_order")
    private int zOrder;

    public CommittedFigureMdbData() {
    }

    public CommittedFigureMdbData(String figureId, int zOrder) {
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getzOrder() {
        return zOrder;
    }
}
