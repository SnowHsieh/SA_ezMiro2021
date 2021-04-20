package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardId extends ValueObject {

    private final String id;

    private BoardId(String id){
        super();
        this.id = id;
    }

    public BoardId(UUID id){
        super();
        this.id = id.toString();
    }

    public String value() {
        return id;
    }

    public static BoardId valueOf(String id){
        return new BoardId(id);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardId))
            return false;

        BoardId target = (BoardId) o;
        return target.id.equals(id) ;
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
