package ntut.csie.sslab.account.users.query.usecase;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<UserDto> findById(String id);
}
