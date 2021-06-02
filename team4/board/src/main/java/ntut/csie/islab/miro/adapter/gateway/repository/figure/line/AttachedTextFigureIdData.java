package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;

import javax.persistence.*;

@Entity
@Table(name = "attachedtextfigure")
public class AttachedTextFigureIdData {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "line_id")
    private String lineId;

    @Column(name = "attached_textfigure_id")
    private String attachedTextFigureId;

    public AttachedTextFigureIdData() {
    }

    public AttachedTextFigureIdData(String lineId, String attachedTextFigureId) {
        this.lineId = lineId;
        this.attachedTextFigureId = attachedTextFigureId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getAttachedTextFigureId() {
        return attachedTextFigureId;
    }
}
