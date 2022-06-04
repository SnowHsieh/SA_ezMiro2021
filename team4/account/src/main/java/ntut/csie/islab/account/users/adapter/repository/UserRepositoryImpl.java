package ntut.csie.islab.account.users.adapter.repository;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.usecase.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private List<User> users;

//    private UserRepositoryPeer peer;

//    public UserRepositoryImpl(UserRepositoryPeer peer) {
//        this.peer = peer;
//    }

    public UserRepositoryImpl() {
        users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public void save(User user) {

        Optional<User> optionalUser = users.stream().filter(s -> s.getUsername().equals(user.getUsername())).findFirst();
        if(optionalUser.isPresent()){
            users.remove(optionalUser.get());
        }
        users.add(user);
    }

    @Override
    public void deleteById(String id) {
        Optional<User> user = users.stream().filter(s -> s.getId().equals(id)).findFirst();
        if (user.isPresent()) {
            users.remove(user);
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return users.stream().filter(s -> s.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return users.stream().filter(s -> s.getEmail().equals(email)).findFirst();
    }
}
