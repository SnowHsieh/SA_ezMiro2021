package ntut.csie.sslab.ddd.usecase;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T, ID> {

    List<T> findAll();
    Optional<T> findById(ID id);
    void save(T data);
    void deleteById(ID id);
}
