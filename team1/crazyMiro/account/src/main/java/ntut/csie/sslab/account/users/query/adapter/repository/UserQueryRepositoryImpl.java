package ntut.csie.sslab.account.users.query.adapter.repository;

import ntut.csie.sslab.account.users.query.usecase.UserQueryRepository;
import ntut.csie.sslab.account.users.query.usecase.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class UserQueryRepositoryImpl implements UserQueryRepository {

    private JdbcTemplate jdbcTemplate;

    public UserQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UserDto> findById(String id) {

        // table name 'user' must be lowercase
        String sql = "select * from user where id = ?";

        List<UserDto> userDtos = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new UserDto(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("role")
                ));

        if (userDtos.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(userDtos.get(0));
    }
}