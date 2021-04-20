package ntut.csie.sslab.account.users.query.usecase;

import ntut.csie.sslab.account.users.query.usecase.UserDto;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<UserDto> findById(String id);
}
