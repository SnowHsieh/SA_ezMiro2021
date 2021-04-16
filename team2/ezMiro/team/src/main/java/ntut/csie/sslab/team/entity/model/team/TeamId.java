package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class TeamId extends ValueObject {

    private final String id;

    private TeamId(String id){
        super();
        this.id = id;
    }

    public TeamId(UUID id){
        super();
        this.id = id.toString();
    }

    public String value() {
        return id;
    }

    public static TeamId valueOf(String id){
        return new TeamId(id);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TeamId))
            return false;

        TeamId target = (TeamId) o;
        return target.id.equals(id) ;
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
