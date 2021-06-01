package ntut.csie.selab.entity.model.widget;

public enum WidgetType {
    LINE("line"),
    STICKY_NOTE("stickyNote");

    private final String type;

    WidgetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
