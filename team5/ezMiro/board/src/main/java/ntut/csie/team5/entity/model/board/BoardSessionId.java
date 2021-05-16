package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardSessionId extends ValueObject {

    private final String id;

    private BoardSessionId(String id) {
        this.id = id;
    }

    public static BoardSessionId create() {
        return new BoardSessionId(UUID.randomUUID().toString());
    }

    public static BoardSessionId valueOf(String id) {
        return new BoardSessionId(id);
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardSessionId))
            return false;

        BoardSessionId target = (BoardSessionId) o;
        return target.id.equals(id) ;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
