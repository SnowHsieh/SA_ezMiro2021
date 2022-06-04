package ntut.csie.islab.account.users.usecase;


import ntut.csie.islab.account.users.entity.User;
import ntut.csie.sslab.ddd.usecase.AbstractRepository;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<User, String> {

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);
}
