package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void update(Long id,User user);
    void delete(Long id);
}
