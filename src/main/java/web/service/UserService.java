package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void update(User user);
    void delete(User user);
}
