package ntut.csie.sslab.kanban.entity.model.workspace;

import ntut.csie.sslab.ddd.model.Entity;

public abstract class Figure extends Entity<String> {
    public Figure(String s) {
        super(s);
    }
}
