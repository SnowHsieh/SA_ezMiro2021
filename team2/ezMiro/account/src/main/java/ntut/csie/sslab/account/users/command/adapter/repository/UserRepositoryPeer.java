package ntut.csie.sslab.account.users.command.adapter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryPeer extends CrudRepository<UserData, String> {
    @Query(value = "select * from user " +
            "where user.username = :username",
            nativeQuery = true)
    UserData getUserByUsername(@Param("username") String username);

    @Query(value = "select * from user " +
            "where user.email = :email",
            nativeQuery = true)
    UserData getUserByEmail(@Param("email") String email);
}