package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public final class ProjectId extends ValueObject {

    private final String id;

    public static ProjectId valueOf(String id){
        return new ProjectId(id);
    }

    private ProjectId(String id){
        super();
        this.id = id;
    }

    public ProjectId(UUID id){
        super();
        this.id = id.toString();
    }

    public String value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProjectId))
            return false;

        ProjectId target = (ProjectId) o;
        return target.id.equals(id) ;
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
