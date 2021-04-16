package ntut.csie.sslab.account.users.query.usecase.user.get;

import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.ddd.usecase.Output;

public interface GetUserOutput extends Output {
    String getId();

    GetUserOutput setId(String id);

    UserDto getUser();

    void setUser(UserDto userDto);
}
