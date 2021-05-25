package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;

import javax.persistence.*;

@Entity
@Table(name="widget")
public class WidgetData {
    @Id
    @Column(name="widget_id")
    private String widgetId;

// TODO relation to board
//    @ManyToOne
//    @JoinColumn(
//            name = "board_id",
//            foreignKey = @ForeignKey(name = "id", value = ConstraintMode.NO_CONSTRAINT)
//    )
    @Column(name="board_id", nullable = false)
    private String boardId;

    @Column(name="top_left_x")
    private int topLeftX;

    @Column(name="top_left_y")
    private int topLeftY;

    @Column(name="bottom_right_x")
    private int bottomRightX;

    @Column(name="bottom_right_y")
    private int bottomRightY;

    @Column(name="color")
    private String color;

    @Column(name="text_color")
    private String textColor;

    @Column(name="text")
    private String text;

    @Column(name="font_size")
    private int fontSize;

    public WidgetData() {
    }

    public WidgetData(
            String widgetId,
            String boardId,
            int topLeftX,
            int topLeftY,
            int bottomRightX,
            int bottomRightY,
            String color,
            String textColor,
            String text,
            int fontSize
    ) {
        this.widgetId = widgetId;
        this.boardId = boardId;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
        this.color = color;
        this.textColor = textColor;
        this.text = text;
        this.fontSize = fontSize;
    }
}
